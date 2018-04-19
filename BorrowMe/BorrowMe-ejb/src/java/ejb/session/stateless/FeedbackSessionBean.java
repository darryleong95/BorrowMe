package ejb.session.stateless;

import entity.CustomerEntity;
import entity.FeedbackEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.FeedbackExistException;
import util.exception.FeedbackNotFoundException;

@Stateless
@Local(FeedbackSessionBeanLocal.class)
public class FeedbackSessionBean implements FeedbackSessionBeanLocal {

    @EJB(name = "RequestSessionBeanLocal")
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public Long createFeedback(FeedbackEntity feedback) throws FeedbackExistException {
        try {

            em.persist(feedback);
            CustomerEntity reviewer = feedback.getReviewer();
            CustomerEntity reviewee = feedback.getReviewee();
            ListingEntity listing = feedback.getListing();
            RequestEntity request = feedback.getRequestEntity();

            request = requestSessionBeanLocal.retrieveRequestByID(request.getRequestEntityId());
            reviewer = customerSessionBeanLocal.retrieveCustomerByCustomerId(reviewer.getCustomerId());
            reviewee = customerSessionBeanLocal.retrieveCustomerByCustomerId(reviewee.getCustomerId());
            listing = listingSessionBeanLocal.retrieveListingById(listing.getListingId());

            if (!reviewer.getCustomerId().equals(request.getCustomerEntity().getCustomerId())) { //lender left fdbk
                request.setLenderLeftFeedback(Boolean.TRUE);
            } else { //borrower left feedback
                request.setBorrowerLeftFeedback(Boolean.TRUE);
                listing.getFeedbacksOnListing().add(feedback);
            }

            reviewer.getFeedbacksGiven().add(feedback);
            System.out.println("reviewer id " + reviewer.getCustomerId() + "getFeedbacksGivenUpdated");
            reviewee.getFeedbacksReceived().add(feedback);
            System.out.println("reviewee id" + reviewee.getCustomerId() + "getFeedbacksReceived updated");

            em.merge(feedback);
            em.flush();
            em.refresh(feedback);

            return feedback.getFeedbackId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new FeedbackExistException("Feedback SQL Integrity problems");
            } else {
                throw new FeedbackExistException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new FeedbackExistException("An unexpected error has occurred: " + ex.getMessage());
        }
    }
    
    //USELESS METHOD?
    @Override
    public FeedbackEntity updateFeedback(FeedbackEntity feedback) throws FeedbackNotFoundException {
        if (feedback.getFeedbackId() != null) {
            FeedbackEntity feedbackToUpdate = retrieveFeedbackById(feedback.getFeedbackId());
            feedbackToUpdate.setRating(feedback.getRating());
            feedbackToUpdate.setReview(feedback.getReview());
            feedbackToUpdate.setReviewer(feedback.getReviewer());
            feedbackToUpdate.setRequestEntity(feedback.getRequestEntity());
            System.out.println("***********************************Check Feedback update***********************************");
            return retrieveFeedbackById(feedback.getFeedbackId());
        } else {
            throw new FeedbackNotFoundException("ID not provided for feedback to be updated");
        }
    }

    @Override
    public FeedbackEntity retrieveFeedbackById(Long id) throws FeedbackNotFoundException {
        FeedbackEntity feedback = em.find(FeedbackEntity.class, id);

        if (feedback != null) {
            return feedback;
        } else {
            throw new FeedbackNotFoundException("Feedback " + id + " does not exist");
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }

}
