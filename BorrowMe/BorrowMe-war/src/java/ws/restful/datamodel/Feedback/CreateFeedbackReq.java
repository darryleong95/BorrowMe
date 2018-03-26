package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createFeedbackReq", propOrder = {
    "feedback"
})

public class CreateFeedbackReq
{    
    private FeedbackEntity feedback;

    
    
    public CreateFeedbackReq()
    {
    }

    
    
    public CreateFeedbackReq(FeedbackEntity feedback)
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
