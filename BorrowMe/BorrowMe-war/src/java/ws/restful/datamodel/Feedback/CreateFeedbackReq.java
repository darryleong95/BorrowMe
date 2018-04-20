/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Feedback;

import entity.FeedbackEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author josh
 */

@XmlRootElement
@XmlType(name = "createRequestReq", propOrder = {
    "feedback",
})

public class CreateFeedbackReq {   
    private FeedbackEntity feedback;
    
    public CreateFeedbackReq() {
     
    }
    
    public CreateFeedbackReq(FeedbackEntity feedback) {
        this.feedback = feedback;
    }

    public FeedbackEntity getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackEntity feedback) {
        this.feedback = feedback;
    }
}
