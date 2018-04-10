package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.ListingEntity;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InvalidListingException;

@Named(value = "viewListingManagedBean")
@ViewScoped
public class ViewListingManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private long listingIdToView;
    private ListingEntity listingToView;

    public ViewListingManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setListingIdToView((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("listingIdToView"));

        try {
            System.err.println("************ FLASH: " + getListingIdToView());
            setListingToView(listingSessionBeanLocal.retrieveListingById(getListingIdToView()));
        } catch (InvalidListingException ex) {
            setListingToView(new ListingEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the listing details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            setListingToView(new ListingEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
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

}
