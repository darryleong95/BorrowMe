package ws.restful.datamodel.Customer;

import entity.CustomerEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "retrieveAllCustomersRsp", propOrder = {
    "customers"
})

public class RetrieveAllCustomersRsp
{   
    private List<CustomerEntity> customers;

    
    
    public RetrieveAllCustomersRsp()
    {
    }

    
    
    public RetrieveAllCustomersRsp(List<CustomerEntity> customers)
    {
        this.customers = customers;
    }

    
    
    public List<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerEntity> customers) {
        this.customers = customers;
    }
}