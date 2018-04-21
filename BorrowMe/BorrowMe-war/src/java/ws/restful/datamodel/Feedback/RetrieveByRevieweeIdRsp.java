/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author josh
 */
@XmlType(name = "retrieveByRevieweeIdRsp", propOrder = {
    "feedbacks"
})
public class RetrieveByRevieweeIdRsp {
    
    private List<FeedbackEntity> feedbacks;

    public RetrieveByRevieweeIdRsp() {
    }

    public RetrieveByRevieweeIdRsp(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
}
