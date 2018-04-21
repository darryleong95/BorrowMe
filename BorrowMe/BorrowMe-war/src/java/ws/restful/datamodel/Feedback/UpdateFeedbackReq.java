package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createFeedbackReq", propOrder = {
    "feedback"
})

public class UpdateFeedbackReq
{    
    private FeedbackEntity feedback;

    
    
    public UpdateFeedbackReq()
    {
    }
    
    public UpdateFeedbackReq(FeedbackEntity feedback)
    {
        this.feedback = feedback;
    }

    
    
    public FeedbackEntity getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackEntity feedback) {
        this.feedback = feedback;
    }
}
