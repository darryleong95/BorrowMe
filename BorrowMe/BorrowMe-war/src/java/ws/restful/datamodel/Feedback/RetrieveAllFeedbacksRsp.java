package ws.restful.datamodel.Feedback;

import ws.restful.datamodel.Feedback.*;
import entity.FeedbackEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "retrieveAllFeedbacksRsp", propOrder = {
    "feedbacks"
})

public class RetrieveAllFeedbacksRsp
{   
    private List<FeedbackEntity> feedbacks;

    
    
    public RetrieveAllFeedbacksRsp()
    {
    }

    
    
    public RetrieveAllFeedbacksRsp(List<FeedbackEntity> feedbacks)
    {
        this.feedbacks = feedbacks;
    }

    
    
    public List<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }
}