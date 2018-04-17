package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "updateCustomerReq", propOrder = {
    "customerEntity"
})

public class UpdateCustomerReq 
{
    private CustomerEntity customerEntity;
    
    public UpdateCustomerReq() 
    {
    }
 
    public UpdateCustomerReq(CustomerEntity customerEntity)
    {
        this.customerEntity = customerEntity;
    }
    
    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }
}
