
package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author josh
 */

@XmlType(name = "retrieveByListingIdRsp", propOrder = {
    "feedbacks"
})
public class RetrieveByListingIdRsp {
    
    private List<FeedbackEntity> feedbacks;

    public RetrieveByListingIdRsp() {
    }

    public RetrieveByListingIdRsp(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
}
