package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.ListingEntity;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import util.enumeration.CategoryEnum;
import util.exception.CreateListingException;
import util.exception.InvalidListingException;

@Named(value = "viewAllListingsManagedBean")
@SessionScoped
public class ViewAllListingsManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private ListingEntity newListing;
    private ListingEntity selectedListingToView;
    private ListingEntity selectedListingToUpdate;
    private List<ListingEntity> listings;
    private List<ListingEntity> filteredListings;

    public ViewAllListingsManagedBean() {
        newListing = new ListingEntity();
        newListing.setListingAvailable(true);
        listings = new ArrayList<>();
        filteredListings = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setListings(listingSessionBeanLocal.retrieveListingList());
        for (ListingEntity l : listings) {
            filteredListings.add(l);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = System.getProperty("user.dir").replace("/config", "/docroot/") + event.getFile().getFileName();

            //String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();
            System.err.println("********** " + System.getProperty("user.dir"));
            System.err.println("********** Demo03ManagedBean.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** Demo03ManagedBean.handleFileUpload(): newFilePath: " + newFilePath);
            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            newListing.getImages().add(newFilePath);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void createNewListing(ActionEvent event) {
        try {
            ListingEntity l = listingSessionBeanLocal.createListing(newListing);
            listings.add(l);
            filteredListings.add(l);
            newListing = new ListingEntity();
            newListing.setListingAvailable(true);
            //STILL NEED TO PUT MEMBER INFO INTO SESSION BEAN, INCOMPLETE
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing created successfully (listing ID: " + l.getListingId() + ")", null));
        } catch (CreateListingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new listing: " + ex.getMessage(), null));
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

    public ListingEntity getNewListing() {
        return newListing;
    }

    public void setNewListing(ListingEntity newListing) {
        this.newListing = newListing;
    }

    public ListingEntity getSelectedListingToView() {
        return selectedListingToView;
    }

    public void setSelectedListingToView(ListingEntity selectedListingToView) {
        this.selectedListingToView = selectedListingToView;
    }

    public ListingEntity getSelectedListingToUpdate() {
        return selectedListingToUpdate;
    }

    public void setSelectedListingToUpdate(ListingEntity selectedListingToUpdate) {
        this.selectedListingToUpdate = selectedListingToUpdate;
    }

    public List<ListingEntity> getListings() {
        return listings;
    }

    public void setListings(List<ListingEntity> listings) {
        this.listings = listings;
    }

    public List<ListingEntity> getFilteredListings() {
        return filteredListings;
    }

    public void setFilteredListings(List<ListingEntity> filteredListings) {
        this.filteredListings = filteredListings;
    }

    public CategoryEnum[] getCategoryEnums() {
        return CategoryEnum.values();
    }

}
