package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
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
    private List<RequestEntity> filteredRequestsMade;
    private List<ListingEntity> listings;
    private RequestEntity selectedRequest;

    public ViewRequestsManagedBean() {
        customer = new CustomerEntity();
        requestsMade = new ArrayList<RequestEntity>();
        listings = new ArrayList<ListingEntity>();
        filteredRequestsMade = new ArrayList<RequestEntity>();
        selectedRequest = new RequestEntity();
    }

    @PostConstruct
    public void postConstruct() {
        CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        try {
            c = customerSessionBeanLocal.retrieveCustomerByCustomerId(c.getCustomerId());
            System.out.println("retrieved customer successfully from context; viewrequests managed bean");
            setRequestsMade(c.getRequestList());
            setListings(c.getListingList());
            for (RequestEntity r : requestsMade) {
                filteredRequestsMade.add(r);
            }
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

    public List<RequestEntity> getFilteredRequestsMade() {
        return filteredRequestsMade;
    }

    public void setFilteredRequestsMade(List<RequestEntity> filteredRequestsMade) {
        this.filteredRequestsMade = filteredRequestsMade;
    }

    public List<ListingEntity> getListings() {
        return listings;
    }

    public void setListings(List<ListingEntity> listings) {
        this.listings = listings;
    }

    public RequestEntity getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(RequestEntity selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

}
