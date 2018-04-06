package ws.restful.datamodel.Feedback;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "createFeedbackRsp", propOrder = {
    "id"
})

public class UpdateFeedbackRsp {

    private Long id;

    public UpdateFeedbackRsp() {
    }

    public UpdateFeedbackRsp(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
