/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FeedbackEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.FeedbackExistException;
import util.exception.FeedbackNotFoundException;
import util.exception.InvalidListingException;

@Local
public interface FeedbackSessionBeanLocal {

    public Long createFeedback(FeedbackEntity feedback) throws FeedbackExistException;

    public FeedbackEntity updateFeedback(FeedbackEntity feedback) throws FeedbackNotFoundException;

    public FeedbackEntity retrieveFeedbackById(Long id) throws FeedbackNotFoundException;

    public FeedbackEntity createFeedbackAPI(FeedbackEntity feedback, Long reviewerId, Long revieweeId, Long listingId) throws CustomerNotFoundException, InvalidListingException;

    public List<FeedbackEntity> retrieveFeedbackByReviewerId(Long customerId);

    public List<FeedbackEntity> retrieveFeedbackByRevieweeId(Long customerId);

    public List<FeedbackEntity> retrieveFeedbackByListingId(Long listingId);

}
