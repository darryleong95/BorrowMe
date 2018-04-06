/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FeedbackEntity;
import javax.ejb.Local;
import util.exception.FeedbackNotFoundException;

@Local
public interface FeedbackSessionBeanLocal {

    public Long createFeedback(FeedbackEntity feedback);

    public FeedbackEntity updateFeedbackAsBorrower(FeedbackEntity feedback) throws FeedbackNotFoundException;
    
    public FeedbackEntity updateFeedbackAsLender(FeedbackEntity feedback) throws FeedbackNotFoundException;

    public FeedbackEntity retrieveFeedback(Long id) throws FeedbackNotFoundException;
    
}
