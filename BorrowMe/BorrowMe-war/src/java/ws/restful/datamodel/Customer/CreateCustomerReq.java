package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "createCustomerReq", propOrder = {
    "customerEntity"
})

public class CreateCustomerReq {

    private CustomerEntity customerEntity;

    public CreateCustomerReq() {
    }

    public CreateCustomerReq(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }
}
