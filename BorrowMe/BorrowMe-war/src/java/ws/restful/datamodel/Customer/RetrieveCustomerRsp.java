package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "retrieveCustomerRsp", propOrder = {
    "customerEntity"
})

public class RetrieveCustomerRsp {

    private CustomerEntity customerEntity;

    public RetrieveCustomerRsp() {
    }

    public RetrieveCustomerRsp(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }
}
