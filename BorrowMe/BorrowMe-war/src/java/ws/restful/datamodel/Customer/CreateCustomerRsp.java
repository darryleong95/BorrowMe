package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "createCustomerRsp", propOrder = {
    "customer"
})

public class CreateCustomerRsp {

    private CustomerEntity customer;

    public CreateCustomerRsp() {
    }

    public CreateCustomerRsp(CustomerEntity customer) {
        this.customer = customer;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
