package ejb.session.stateless;

import entity.CustomerEntity;
import entity.FeedbackEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CustomerNotFoundException;
import util.exception.FeedbackNotFoundException;

@Stateless
@Local(FeedbackSessionBeanLocal.class)
public class FeedbackSessionBean implements FeedbackSessionBeanLocal {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public Long createFeedback(FeedbackEntity feedback) {

        em.persist(feedback);
        em.flush();
        em.refresh(feedback);

        return feedback.getFeedbackId();
    }

    @Override
    public FeedbackEntity updateFeedbackAsBorrower(FeedbackEntity feedback) throws FeedbackNotFoundException {
        if (feedback.getFeedbackId() != null) {
            FeedbackEntity feedbackToUpdate = retrieveFeedback(feedback.getFeedbackId());
            feedbackToUpdate.setBorrowerReviewLender(feedback.getBorrowerReviewLender());
            feedbackToUpdate.setBorrowerReviewLenderRating(feedback.getBorrowerReviewLenderRating());
            feedbackToUpdate.setListingRating(feedback.getListingRating());
            feedbackToUpdate.setListingReview(feedback.getListingReview());
            System.out.println("***********************************Check Feedback update***********************************");
            return retrieveFeedback(feedback.getFeedbackId());
        } else {
            throw new FeedbackNotFoundException("ID not provided for feedback to be updated");
        }
    }

    @Override
    public FeedbackEntity updateFeedbackAsLender(FeedbackEntity feedback) throws FeedbackNotFoundException {
        if (feedback.getFeedbackId() != null) {
            FeedbackEntity feedbackToUpdate = retrieveFeedback(feedback.getFeedbackId());
            feedbackToUpdate.setLenderReviewBorrower(feedback.getLenderReviewBorrower());
            feedbackToUpdate.setLenderReviewBorrowerRating(feedback.getLenderReviewBorrowerRating());
            System.out.println("***********************************Check Feedback update***********************************");
            return retrieveFeedback(feedback.getFeedbackId());
        } else {
            throw new FeedbackNotFoundException("ID not provided for feedback to be updated");
        }
    }

    @Override
    public FeedbackEntity retrieveFeedback(Long id) throws FeedbackNotFoundException {
        FeedbackEntity feedback = em.find(FeedbackEntity.class, id);

        if (feedback != null) {
            return feedback;
        } else {
            throw new FeedbackNotFoundException("Feedback " + id + " does not exist");
        }
    }

    @Override
    public List<FeedbackEntity> retrieveListOfFeedback(Long customerId) throws CustomerNotFoundException {
        try {
            CustomerEntity customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);
            return customer.getFeedbackList();
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer " + customerId + " cannot be found");
        }

    }

}
