package ws.restful.datamodel.Feedback;

import ws.restful.datamodel.Feedback.*;
import entity.FeedbackEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "createBookRsp", propOrder = {
    "id"
})

public class CreateFeedbackRsp
{    
    private FeedbackEntity feedbackEntity;

    
    
    public CreateFeedbackRsp()
    {
    }

    
    
    public CreateFeedbackRsp(FeedbackEntity feedbackEntity)
    {
        this.feedbackEntity = feedbackEntity;
    }

    
    
    public FeedbackEntity getFeedbackEntity() {
        return feedbackEntity;
    }

    public void setFeedbackEntity(FeedbackEntity feedbackEntity) {
        this.feedbackEntity = feedbackEntity;
    }
}