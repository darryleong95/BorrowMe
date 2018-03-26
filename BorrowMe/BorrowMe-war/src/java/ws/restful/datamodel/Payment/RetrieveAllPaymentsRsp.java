package ws.restful.datamodel.Payment;

import entity.PaymentEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "retrieveAllPaymentsRsp", propOrder = {
    "payments"
})

public class RetrieveAllPaymentsRsp
{   
    private List<PaymentEntity> payments;

    
    
    public RetrieveAllPaymentsRsp()
    {
    }

    
    
    public RetrieveAllPaymentsRsp(List<PaymentEntity> payments)
    {
        this.payments = payments;
    }

    
    
    public List<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentEntity> payments) {
        this.payments = payments;
    }
}