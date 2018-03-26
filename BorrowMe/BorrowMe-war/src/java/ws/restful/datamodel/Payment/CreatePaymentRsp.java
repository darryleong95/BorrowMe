package ws.restful.datamodel.Payment;

import entity.PaymentEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "createBookRsp", propOrder = {
    "id"
})

public class CreatePaymentRsp
{    
    private PaymentEntity paymentEntity;

    
    
    public CreatePaymentRsp()
    {
    }

    
    
    public CreatePaymentRsp(PaymentEntity paymentEntity)
    {
        this.paymentEntity = paymentEntity;
    }

    
    
    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }
}