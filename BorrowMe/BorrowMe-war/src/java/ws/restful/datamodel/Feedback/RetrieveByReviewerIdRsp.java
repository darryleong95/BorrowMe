
package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author josh
 */

@XmlType(name = "retrieveByReviewerIdRsp", propOrder = {
    "feedbacks"
})
public class RetrieveByReviewerIdRsp {
    
    private List<FeedbackEntity> feedbacks;

    public RetrieveByReviewerIdRsp() {
    }

    public RetrieveByReviewerIdRsp(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
}
