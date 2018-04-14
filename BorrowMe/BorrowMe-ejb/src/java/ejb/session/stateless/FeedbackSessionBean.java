package ejb.session.stateless;

import entity.FeedbackEntity;
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

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public Long createFeedback(FeedbackEntity feedback) throws FeedbackExistException{
        try {
            em.persist(feedback);
            em.flush();
            em.refresh(feedback);

            return feedback.getFeedbackId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new FeedbackExistException("Feedback with same name already exists");
            } else {
                throw new FeedbackExistException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new FeedbackExistException("An unexpected error has occurred: " + ex.getMessage());
        }
    }


@Override
        public FeedbackEntity updateFeedback(FeedbackEntity feedback) throws FeedbackNotFoundException {
        if (feedback.getFeedbackId() != null) {
            FeedbackEntity feedbackToUpdate = retrieveFeedbackById(feedback.getFeedbackId());
            feedbackToUpdate.setRating(feedback.getRating());
            feedbackToUpdate.setReview(feedback.getReview());
            feedbackToUpdate.setCustomerEntity(feedback.getCustomerEntity());
            feedbackToUpdate.setRequestEntity(feedback.getRequestEntity());
            System.out.println("***********************************Check Feedback update***********************************");
            return retrieveFeedbackById(feedback.getFeedbackId());
        } else {
            throw new FeedbackNotFoundException("ID not provided for feedback to be updated");
        }
    }

    @Override
        public FeedbackEntity 

retrieveFeedbackById(Long id) throws FeedbackNotFoundException {
        FeedbackEntity feedback = em.find(FeedbackEntity.class
, id);

        if (feedback != null) {
            return feedback;
        } else {
            throw new FeedbackNotFoundException("Feedback " + id + " does not exist");
        }
    }

}
