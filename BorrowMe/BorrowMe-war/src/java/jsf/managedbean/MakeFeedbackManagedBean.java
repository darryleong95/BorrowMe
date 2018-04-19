package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.FeedbackSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.CustomerEntity;
import entity.FeedbackEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CustomerNotFoundException;
import util.exception.FeedbackExistException;
import util.exception.InvalidListingException;
import util.exception.RequestNotFoundException;

@Named(value = "makeFeedbackManagedBean")
@ViewScoped
public class MakeFeedbackManagedBean implements Serializable {

    @EJB(name = "FeedbackSessionBeanLocal")
    private FeedbackSessionBeanLocal feedbackSessionBeanLocal;

    @EJB(name = "RequestSessionBeanLocal")
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private CustomerEntity customer;
    private FeedbackEntity newFeedback;
    private RequestEntity request;
    private ListingEntity listing;

    public MakeFeedbackManagedBean() {
        newFeedback = new FeedbackEntity();
        request = new RequestEntity();
        customer = new CustomerEntity();
        listing = new ListingEntity();
    }

    @PostConstruct
    public void postConstruct() {
        CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        try {
            customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(c.getCustomerId());
            System.out.println("retrieved customer successfully from context; approverequest managed bean");
            RequestEntity requestEntity = (RequestEntity) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("requestEntity");

//            if (requestEntity == null) {
//                System.out.println("requestentity is null");
//            } else if (requestEntity.getRequestEntityId() == null) {
//                System.out.println("requestentity id is null");
//            } else {
//                System.out.println("requestentity id obtained is" + requestEntity.getRequestEntityId());
//            }
            request = requestSessionBeanLocal.retrieveRequestByID(requestEntity.getRequestEntityId());
            listing = listingSessionBeanLocal.retrieveListingById(getRequest().getListingEntity().getListingId());
        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer not found??? :" + ex.getMessage());
        } catch (RequestNotFoundException ex) {
            System.out.println("Request retrieved not found :" + ex.getMessage());
        } catch (InvalidListingException ex) {
            System.out.println("listing retrieved not found:" + ex.getMessage());
        }
    }

    public void makeFeedback(ActionEvent event) {
        try {
            CustomerEntity reviewer = customer;
            newFeedback.setReviewer(reviewer);
            newFeedback.setRequestEntity(request);
            newFeedback.setListing(request.getListingEntity());
            if (request.getCustomerEntity().getCustomerId().equals(reviewer.getCustomerId())) {
                System.out.println("im the rentee " + reviewer.getUsername() + ", reviewing the renter");
                CustomerEntity reviewee = request.getListingEntity().getCustomerEntity();
                System.out.println("reviewee is: " + reviewee.getUsername());
                newFeedback.setReviewee(reviewee);
            } else {
                System.out.println("else im the renter + " + reviewer.getUsername() + ", reviewing the rentee");
                CustomerEntity reviewee = request.getCustomerEntity();
                System.out.println("reviewee is: " + reviewee.getUsername());
                newFeedback.setReviewee(reviewee);
            }
            Long newFeedbackId = feedbackSessionBeanLocal.createFeedback(newFeedback);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New feedback created successfully (feedback ID: " + newFeedbackId + ")", null));
        } catch (FeedbackExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new feedback: " + ex.getMessage(), null));
        }
    }

    public FeedbackEntity getFeedback() {
        return newFeedback;
    }

    public void setFeedback(FeedbackEntity newFeedback) {
        this.newFeedback = newFeedback;
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

}
