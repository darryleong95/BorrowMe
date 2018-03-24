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
    public boolean deleteFeedback(FeedbackEntity feedback) {

        //assumption that this feedback object already exists;
        em.remove(feedback);
        return true;

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
