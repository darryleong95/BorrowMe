package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "retrieveCustomerRsp", propOrder = {
    "customer"
})

public class RetrieveCustomerRsp {

    private CustomerEntity customer;

    public RetrieveCustomerRsp() {
    }

    public RetrieveCustomerRsp(CustomerEntity customer) {
        this.customer = customer;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
