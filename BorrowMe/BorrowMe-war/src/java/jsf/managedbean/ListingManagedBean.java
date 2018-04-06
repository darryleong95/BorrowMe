
package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.ListingEntity;
import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import util.enumeration.CategoryEnum;
import util.exception.CreateListingException;
import util.exception.InvalidListingException;

@Named(value = "listingManagedBean")
@SessionScoped
public class ListingManagedBean implements Serializable {
    
    //NOT GOING TO BE USED ANYMORE
    //GOING TO BE RETRANSITIONED TO VIEW ALL LISTINGS MANAGED BEAN

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private ListingEntity newListing;
    private ListingEntity selectedListingToView;
    private ListingEntity selectedListingToUpdate;
    private List<ListingEntity> listings;
    private List<ListingEntity> filteredListings;

    private List<SelectItem> categoryEnum;

    /**
     * Creates a new instance of ListingManagedBean
     */
    public ListingManagedBean() {
        newListing = new ListingEntity();
        listings = new ArrayList<>();
        filteredListings = new ArrayList<>();
        categoryEnum = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setListings(listingSessionBeanLocal.retrieveListingList());
        setFilteredListings(getListings());
        getCategoryEnum().add(new SelectItem(CategoryEnum.ELECTRONICS, CategoryEnum.ELECTRONICS.toString()));
        getCategoryEnum().add(new SelectItem(CategoryEnum.OTHERS, CategoryEnum.OTHERS.toString()));
        getCategoryEnum().add(new SelectItem(CategoryEnum.PARTY, CategoryEnum.PARTY.toString()));
        getCategoryEnum().add(new SelectItem(CategoryEnum.SPORTS, CategoryEnum.SPORTS.toString()));
        getCategoryEnum().add(new SelectItem(CategoryEnum.VEHICLES, CategoryEnum.VEHICLES.toString()));
    }

    public void saveNewListing(ActionEvent event) {
        try {
            Long newListingId = listingSessionBeanLocal.createListing(newListing).getListingId();
            newListing.setListingId(newListingId);
            listings.add(newListing);

            newListing = new ListingEntity();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing with Id" + newListingId + " created successfully", null));

        } catch (CreateListingException ex) {
        }
    }

    public void saveListing(ActionEvent event) {
        listingSessionBeanLocal.updateListing(getSelectedListingToUpdate());

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing " + getSelectedListingToUpdate().getListingId() + " updated successfully", null));
    }

    public void deleteListing(javax.faces.event.ActionEvent event) {
        ListingEntity listingToDelete = (ListingEntity) event.getComponent().getAttributes().get("listingToDelete");

        try {
            listingSessionBeanLocal.deleteListing(listingToDelete.getListingId());
        } catch (InvalidListingException ex) {
            Logger.getLogger(ListingManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        getListings().remove(listingToDelete);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing with listing Id" + listingToDelete.getListingId() + " deleted successfully", "Listing with listing Id" + listingToDelete.getListingId() + " deleted successfully"));
    }

    /**
     * @return the newListing
     */
    public ListingEntity getNewListing() {
        return newListing;
    }

    /**
     * @param newListing the newListing to set
     */
    public void setNewListing(ListingEntity newListing) {
        this.newListing = newListing;
    }

    /**
     * @return the selectedListingToView
     */
    public ListingEntity getSelectedListingToView() {
        return selectedListingToView;
    }

    /**
     * @param selectedListingToView the selectedListingToView to set
     */
    public void setSelectedListingToView(ListingEntity selectedListingToView) {
        this.selectedListingToView = selectedListingToView;
    }

    /**
     * @return the selectedListingToUpdate
     */
    public ListingEntity getSelectedListingToUpdate() {
        return selectedListingToUpdate;
    }

    /**
     * @param selectedListingToUpdate the selectedListingToUpdate to set
     */
    public void setSelectedListingToUpdate(ListingEntity selectedListingToUpdate) {
        this.selectedListingToUpdate = selectedListingToUpdate;
    }

    /**
     * @return the listings
     */
    public List<ListingEntity> getListings() {
        return listings;
    }

    /**
     * @param listings the listings to set
     */
    public void setListings(List<ListingEntity> listings) {
        this.listings = listings;
    }

    /**
     * @return the filteredListings
     */
    public List<ListingEntity> getFilteredListings() {
        return filteredListings;
    }

    /**
     * @param filteredListings the filteredListings to set
     */
    public void setFilteredListings(List<ListingEntity> filteredListings) {
        this.filteredListings = filteredListings;
    }

    /**
     * @return the categoryEnum
     */
    public List<SelectItem> getCategoryEnum() {
        return categoryEnum;
    }

    /**
     * @param categoryEnum the categoryEnum to set
     */
    public void setCategoryEnum(List<SelectItem> categoryEnum) {
        this.categoryEnum = categoryEnum;
    }

}
