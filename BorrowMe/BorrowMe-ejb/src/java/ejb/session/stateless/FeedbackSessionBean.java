package ejb.session.stateless;

import entity.FeedbackEntity;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.FeedbackNotFoundException;

@Stateless
@Local(FeedbackSessionBeanLocal.class)
public class FeedbackSessionBean implements FeedbackSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public FeedbackEntity createFeedback(FeedbackEntity feedback) {

        em.persist(feedback);
        em.flush();
        em.refresh(feedback);

        return feedback;
    }

    @Override
    public FeedbackEntity updateFeedback(FeedbackEntity feedback) {

        em.merge(feedback);
        return feedback;

    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        FeedbackEntity feedbackEntity = em.find(FeedbackEntity.class, feedbackId);
        em.remove(feedbackEntity);
    }
    
    @Override
    public void removeListingFeedback(Long feedbackId){
        //remove feeback + rating
        FeedbackEntity feedbackEntity = em.find(FeedbackEntity.class, feedbackId);
        feedbackEntity.setListingRating(null);
        feedbackEntity.setListingReview(null);
        updateFeedback(feedbackEntity);
    }
    
    @Override
    public void removeLenderReviewBorrowerFeedback(Long feedbackId){
        //remove feeback + rating
        FeedbackEntity feedbackEntity = em.find(FeedbackEntity.class, feedbackId);
        feedbackEntity.setLenderReviewBorrowerRating(null);
        feedbackEntity.setLenderReviewBorrower(null);
        updateFeedback(feedbackEntity);
    }
    
    @Override
    public void removeBorrowerReviewLenderFeedback(Long feedbackId){
        //remove feeback + rating
        FeedbackEntity feedbackEntity = em.find(FeedbackEntity.class, feedbackId);
        feedbackEntity.setBorrowerReviewLenderRating(null);
        feedbackEntity.setBorrowerReviewLender(null);
        updateFeedback(feedbackEntity);
    }


    @Override
    public FeedbackEntity retrieveFeedbackById (Long feedbackEntityId) throws FeedbackNotFoundException {

        FeedbackEntity feedback = em.find(FeedbackEntity.class, feedbackEntityId);
        if (feedback == null) {
            throw new FeedbackNotFoundException("Feedback not found! \n");
        }
        return feedback;

    }

}
