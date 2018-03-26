package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createCustomerReq", propOrder = {
    "customer"
})

public class CreateCustomerReq
{    
    private CustomerEntity customer;

    
    
    public CreateCustomerReq()
    {
    }

    
    
    public CreateCustomerReq(CustomerEntity customer)
    {
        this.customer = customer;
    }

    
    
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
