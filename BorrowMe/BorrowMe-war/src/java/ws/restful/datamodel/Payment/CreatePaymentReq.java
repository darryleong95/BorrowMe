package ws.restful.datamodel.Payment;

import entity.PaymentEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createPaymentReq", propOrder = {
    "payment"
})

public class CreatePaymentReq
{    
    private PaymentEntity payment;

    
    
    public CreatePaymentReq()
    {
    }

    public CreatePaymentReq(PaymentEntity payment)
    {
        this.payment = payment;
    }
    
    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }
}
