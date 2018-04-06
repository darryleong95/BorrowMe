package ws.restful.datamodel.Payment;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "createPaymentRsp", propOrder = {
    "id"
})

public class CreatePaymentRsp {

    private Long id;

    public CreatePaymentRsp() {
    }

    public CreatePaymentRsp(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
