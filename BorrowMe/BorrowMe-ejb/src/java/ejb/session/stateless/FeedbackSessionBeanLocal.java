/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FeedbackEntity;
import javax.ejb.Local;
import util.exception.FeedbackExistException;
import util.exception.FeedbackNotFoundException;

@Local
public interface FeedbackSessionBeanLocal {

    public Long createFeedback(FeedbackEntity feedback) throws FeedbackExistException;

    public FeedbackEntity updateFeedback(FeedbackEntity feedback) throws FeedbackNotFoundException;

    public FeedbackEntity retrieveFeedbackById(Long id) throws FeedbackNotFoundException;

}
