package ejb.session.stateless;

import entity.CustomerEntity;
import entity.FeedbackEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.FeedbackExistException;
import util.exception.FeedbackNotFoundException;
import util.exception.InvalidListingException;

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

            feedback.setReviewee(reviewee);
            feedback.setReviewer(reviewer);
            feedback.setListing(listing);
            feedback.setRequestEntity(request);
            if (!reviewer.getCustomerId().equals(request.getCustomerEntity().getCustomerId())) { //lender left fdbk
                request.setLenderLeftFeedback(Boolean.TRUE);
            } else { //borrower left feedback
                request.setBorrowerLeftFeedback(Boolean.TRUE);
                listing.getFeedbacksOnListing().add(feedback);
                //request only has a list of feedback from borrowers who rented it!
                //feedback from renter about renters not needed to show
                            request.getFeedbackList().add(feedback);
            
            }

            reviewer.getFeedbacksGiven().add(feedback);
            System.out.println("reviewer id " + reviewer.getCustomerId() + "getFeedbacksGivenUpdated");
            reviewee.getFeedbacksReceived().add(feedback);
            System.out.println("reviewee id" + reviewee.getCustomerId() + "getFeedbacksReceived updated");
            listing.getFeedbacksOnListing().add(feedback);

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
    
    @Override
    public FeedbackEntity createFeedbackAPI(FeedbackEntity feedback, Long reviewerId, Long revieweeId, Long listingId) throws CustomerNotFoundException, InvalidListingException {
        if(reviewerId != revieweeId){
            CustomerEntity reviewer = customerSessionBeanLocal.retrieveCustomerByCustomerId(reviewerId);
            CustomerEntity reviewee = customerSessionBeanLocal.retrieveCustomerByCustomerId(revieweeId);   
            ListingEntity listing = listingSessionBeanLocal.retrieveListingById(listingId);
            List<RequestEntity> requests = new ArrayList<>();
            requests = requestSessionBeanLocal.retrieveRequestListByCustomerID(reviewerId);
            RequestEntity request;
            for(int i = 0; i < requests.size(); i++) {
                RequestEntity j = requests.get(i);
                if(j.getListingEntity().getListingId() == listingId) {
                    request = j; 
                    request.setBorrowerLeftFeedback(true);
                    feedback.setRequestEntity(request);
                }
            }
            feedback.setListing(listing);
            feedback.setReviewer(reviewer);
            feedback.setReviewee(reviewee);
            em.persist(feedback);
            em.flush();
            em.refresh(feedback);
            return feedback;
        } else {
            return null;
        }
    }

    @Override
    public List<FeedbackEntity> retrieveFeedbackByReviewerId(Long customerId) {
        Query query = em.createQuery("SELECT c FROM FeedbackEntity c WHERE c.reviewer.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        return query.getResultList();   
    }
 
    @Override
    public List<FeedbackEntity> retrieveFeedbackByRevieweeId(Long customerId) {
        Query query = em.createQuery("SELECT c FROM FeedbackEntity c WHERE c.reviewee.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        return query.getResultList();   
    }

    @Override
    public List<FeedbackEntity> retrieveFeedbackByListingId(Long listingId) {
        try {
            ListingEntity ls = listingSessionBeanLocal.retrieveListingById(listingId);
            Query query = em.createQuery("SELECT c FROM FeedbackEntity c WHERE c.listing = :inListingID");
            query.setParameter("inListingID", ls);
            return query.getResultList();
        } catch (InvalidListingException ex) {
            Logger.getLogger(RequestSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }  

    public void persist(Object object) {
        em.persist(object);
    }

    

}
