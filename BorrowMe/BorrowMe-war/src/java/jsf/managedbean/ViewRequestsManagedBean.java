package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.PaymentSessionBeanLocal;
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidListingException;
import util.exception.RequestNotFoundException;

@Named(value = "viewRequestsManagedBean")
@ViewScoped
public class ViewRequestsManagedBean implements Serializable {

    @EJB(name = "RequestSessionBeanLocal")
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB(name = "PaymentSessionBeanLocal")
    private PaymentSessionBeanLocal paymentSessionBeanLocal;

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
            requestsMade = c.getRequestList();
            listings = c.getListingList();
            for (RequestEntity r : requestsMade) {
                filteredRequestsMade.add(r);
            }
        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer not found in post construct of view requests managed bean");
        }
    }

    public void redirectUpdateRequestStatus(ActionEvent event) {
        System.out.println("inside redirect method");

        RequestEntity requestEntity = (RequestEntity) event.getComponent().getAttributes().get("requestEntity");
        System.out.println("requestEntityId from obtained request entity: " + requestEntity.getRequestEntityId());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("requestEntity", requestEntity);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ApproveRequest.xhtml");
        } catch (IOException ex) {
            System.out.println("Error with facial recognition/voice software");
        }
    }

    public void makePayment(ActionEvent event) {
        selectedRequest = (RequestEntity) event.getComponent().getAttributes().get("requestEntity");

        paymentSessionBeanLocal.updatePayment(selectedRequest.getPaymentEntity());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment of " + selectedRequest.getPaymentEntity().getTotalAmount() + " is successfully made!", null));
    }

    public void redirectMakeFeedback(ActionEvent event) throws IOException {
        RequestEntity requestEntity = (RequestEntity) event.getComponent().getAttributes().get("requestEntity");
        System.out.println("requestEntityId from obtained request entity: " + requestEntity.getRequestEntityId());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("requestEntity", requestEntity);

        FacesContext.getCurrentInstance().getExternalContext().redirect("MakeFeedback.xhtml");
    }

    public void deleteRequest(ActionEvent event) {
        try {
            RequestEntity requestToDelete = (RequestEntity) event.getComponent().getAttributes().get("requestToDelete");
            requestSessionBeanLocal.deleteRequest(requestToDelete.getRequestEntityId());
            requestsMade.remove(requestToDelete);
            filteredRequestsMade.remove(requestToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Request deleted successfully", null));
        } catch (CustomerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting request(customer not found): " + ex.getMessage(), null));
        } catch (RequestNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting request (invalid request): " + ex.getMessage(), null));
        } catch (InvalidListingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting request (invalid listing): " + ex.getMessage(), null));
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
