package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "updateCustomerReq", propOrder = {
    "customer"
})

public class UpdateCustomerReq 
{
    private CustomerEntity customer;

    
    
    public UpdateCustomerReq() 
    {
    }

    
    
    public UpdateCustomerReq(CustomerEntity customer)
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
