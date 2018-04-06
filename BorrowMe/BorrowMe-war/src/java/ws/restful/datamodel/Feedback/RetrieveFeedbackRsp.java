package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "retrieveFeedbackRsp", propOrder = {
    "feedback"
})

public class RetrieveFeedbackRsp {

    private FeedbackEntity feedback;

    public RetrieveFeedbackRsp() {
    }

    public RetrieveFeedbackRsp(FeedbackEntity feedback) {
        this.feedback = feedback;
    }

    public FeedbackEntity getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackEntity feedback) {
        this.feedback = feedback;
    }
}
