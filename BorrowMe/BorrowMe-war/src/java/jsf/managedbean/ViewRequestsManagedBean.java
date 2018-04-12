package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import entity.RequestEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CustomerNotFoundException;

@Named(value = "viewRequestsManagedBean")
@ViewScoped
public class ViewRequestsManagedBean implements Serializable {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    
    
    private CustomerEntity customer;
    private List<RequestEntity> requestsMade;
    
    
    public ViewRequestsManagedBean() {
        customer = new CustomerEntity();
        requestsMade = new ArrayList<RequestEntity>();
        
    }
    
    @PostConstruct
    public void postConstruct() {
        CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        try {
        c = customerSessionBeanLocal.retrieveCustomerByCustomerId(c.getCustomerId());
        setRequestsMade(c.getRequestList());
        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer not found in post construct of view requests managed bean");
        }
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<RequestEntity> getRequestsMade() {
        return requestsMade;
    }

    public void setRequestsMade(List<RequestEntity> requestsMade) {
        this.requestsMade = requestsMade;
    }
    
}
