package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.io.IOException;
import java.io.Serializable;
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

@Named(value = "approveRequestManagedBean")
@ViewScoped
public class ApproveRequestManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "RequestSessionBeanLocal")
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private RequestEntity request;
    private CustomerEntity customer;
    private ListingEntity listing;
    private boolean accepted;

    public ApproveRequestManagedBean() {
        request = new RequestEntity();
        customer = new CustomerEntity();
        listing = new ListingEntity();
    }

    @PostConstruct
    public void postConstruct() {
        CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        try {
            c = customerSessionBeanLocal.retrieveCustomerByCustomerId(c.getCustomerId());
            System.out.println("retrieved customer successfully from context; approverequest managed bean");
            RequestEntity requestEntity = (RequestEntity) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("requestEntity");
//            if (requestEntity == null) {
//                System.out.println("requestentity is null");
//            } else if (requestEntity.getRequestEntityId() == null) {
//                System.out.println("requestentity id is null");
//            } else {
//                System.out.println("requestentity id obtained is" + requestEntity.getRequestEntityId());
//            }
            setRequest(requestSessionBeanLocal.retrieveRequestByID(requestEntity.getRequestEntityId()));
            setListing(listingSessionBeanLocal.retrieveListingById(getRequest().getListingEntity().getListingId()));
        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer not found??? :" + ex.getMessage());
        } catch (RequestNotFoundException ex) {
            System.out.println("Request retrieved not found :" + ex.getMessage());
        } catch (InvalidListingException ex) {
            System.out.println("listing retrieved not found:" + ex.getMessage());
        }
    }

    public void updateRequestStatus(ActionEvent event) {
        System.out.println("@ update request status method");
        if (accepted) {
            System.out.println("boolean turned accepted");
        } else {
            System.out.println("boolean turned false");
        }
        request.setAcknowledged(true);
        if (accepted) {
           request.setAccepted(true);
        } else {
            request.setAccepted(false);
        }
        requestSessionBeanLocal.updateRequest(getRequest());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Status successfully set", null));
    }

 

    public RequestEntity getRequest() {
        return request;
    }

    public void setRequest(RequestEntity request) {
        this.request = request;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public ListingEntity getListing() {
        return listing;
    }

    public void setListing(ListingEntity listing) {
        this.listing = listing;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

}
