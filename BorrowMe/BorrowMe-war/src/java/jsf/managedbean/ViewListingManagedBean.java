package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import util.exception.CreateRequestException;
import util.exception.InvalidListingException;

@Named(value = "viewListingManagedBean")
@ViewScoped
public class ViewListingManagedBean implements Serializable {

    @EJB(name = "RequestSessionBeanLocal")
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private long listingIdToView;
    private ListingEntity listingToView;
    private RequestEntity newRequestEntity;
    private Date newStartDate;
    private Date newEndDate;
    private String dateDiffValue;
    private List<RequestEntity> requests;
    private List<RequestEntity> filteredRequests;
    private boolean accepted;

    public ViewListingManagedBean() {
        listingToView = new ListingEntity();
        newRequestEntity = new RequestEntity();
        dateDiffValue = "(num of days rented must be >= 1!)";
        requests = new ArrayList<RequestEntity>();
        filteredRequests = new ArrayList<RequestEntity>();
    }

    @PostConstruct
    public void postConstruct() {
        setListingIdToView((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("listingIdToView"));

        try {
//            System.err.println("************ FLASH: " + getListingIdToView());
            setListingToView(listingSessionBeanLocal.retrieveListingById(getListingIdToView()));
            requests = listingToView.getRequestList();
            for (RequestEntity r : requests) {
                filteredRequests.add(r);
            }
        } catch (InvalidListingException ex) {
            setListingToView(new ListingEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the listing details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            setListingToView(new ListingEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void dateDiff(SelectEvent event) {

        if (newStartDate != null && newEndDate != null) {
            //HH converts hour in 24 hours format (0-23), day calculation
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            Date d1 = null;
            Date d2 = null;

            try {
//                    d1 = format.parse(newStartDate);
//                    d2 = format.parse(newEndDate);
                d1 = newStartDate;
                d2 = newEndDate;

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000) + 1;

                setDateDiffValue(String.valueOf(diffDays));

            } catch (Exception ex) {
                System.out.println("ERROR AT DATE DIFF: " + ex.getMessage());
            }
        }
    }

    public void createRequest(ActionEvent event) {
        try {
            CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
            newRequestEntity.setCustomerEntity(c);
            newRequestEntity.setListingEntity(listingToView);
            newRequestEntity.setStartDate(newStartDate);
            newRequestEntity.setEndDate(newEndDate);
            newRequestEntity.setNoOfDays(Integer.valueOf(getDateDiffValue()));
            RequestEntity r = requestSessionBeanLocal.createRequest(newRequestEntity);
            requests.add(r);
            filteredRequests.add(r);
            newRequestEntity = new RequestEntity();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing created successfully (request ID: " + r.getRequestEntityId() + ")", null));
        } catch (CreateRequestException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new request: " + ex.getMessage(), null));
        }
    }
    
    public void changeAcceptance() {
        String summary = accepted ? "Approved" : "Rejected";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    

    public long getListingIdToView() {
        return listingIdToView;
    }

    public void setListingIdToView(long listingIdToView) {
        this.listingIdToView = listingIdToView;
    }

    public ListingEntity getListingToView() {
        return listingToView;
    }

    public void setListingToView(ListingEntity listingToView) {
        this.listingToView = listingToView;
    }

    public RequestEntity getNewRequestEntity() {
        return newRequestEntity;
    }

    public void setNewRequestEntity(RequestEntity newRequestEntity) {
        this.newRequestEntity = newRequestEntity;
    }

    public Date getNewStartDate() {
        return newStartDate;
    }

    public void setNewStartDate(Date newStartDate) {
        this.newStartDate = newStartDate;
    }

    public Date getNewEndDate() {
        return newEndDate;
    }

    public void setNewEndDate(Date newEndDate) {
        this.newEndDate = newEndDate;
    }

    public String getDateDiffValue() {
        return dateDiffValue;
    }

    public void setDateDiffValue(String dateDiffValue) {
        this.dateDiffValue = dateDiffValue;
    }

    public List<RequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }

    public List<RequestEntity> getFilteredRequests() {
        return filteredRequests;
    }

    public void setFilteredRequests(List<RequestEntity> filteredRequests) {
        this.filteredRequests = filteredRequests;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

}
