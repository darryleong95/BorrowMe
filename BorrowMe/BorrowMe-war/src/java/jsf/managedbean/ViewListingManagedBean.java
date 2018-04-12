package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.ListingEntity;
import entity.RequestEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import util.exception.InvalidListingException;

@Named(value = "viewListingManagedBean")
@ViewScoped
public class ViewListingManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private long listingIdToView;
    private ListingEntity listingToView;
    private RequestEntity newRequestEntity;
    private Date newStartDate;
    private Date newEndDate;
    private String dateDiffValue;

    public ViewListingManagedBean() {
        listingToView = new ListingEntity();
        newRequestEntity = new RequestEntity();
        dateDiffValue = "(num of days rented must be >= 1!)";
    }

    @PostConstruct
    public void postConstruct() {
        setListingIdToView((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("listingIdToView"));

        try {
//            System.err.println("************ FLASH: " + getListingIdToView());
            setListingToView(listingSessionBeanLocal.retrieveListingById(getListingIdToView()));
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

}
