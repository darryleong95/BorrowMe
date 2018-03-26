package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "createBookRsp", propOrder = {
    "id"
})

public class CreateCustomerRsp
{    
    private CustomerEntity customerEntity;

    
    
    public CreateCustomerRsp()
    {
    }

    
    
    public CreateCustomerRsp(CustomerEntity customerEntity)
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