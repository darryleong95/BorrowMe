package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import util.exception.CreateListingException;
import util.exception.InvalidFileTypeException;
import util.exception.InvalidListingException;

@Named(value = "viewAllListingsManagedBean")
@ViewScoped
public class ViewAllListingsManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private CustomerEntity customerEntity;
    private ListingEntity newListing;
    private ListingEntity selectedListingToView;
    private ListingEntity selectedListingToUpdate;
    private List<ListingEntity> listings;
    private List<ListingEntity> filteredListings;
    
    private List<String> categories;

    public ViewAllListingsManagedBean() {
        newListing = new ListingEntity();
        newListing.setListingAvailable(true);
        listings = new ArrayList<>();
        filteredListings = new ArrayList<>();
        categories = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setListings(listingSessionBeanLocal.retrieveListingList());
        for (ListingEntity l : listings) {
            filteredListings.add(l);
        }
        getCategories().add("PARTY");
        getCategories().add("ELECTRONICS");
        getCategories().add("SPORTS");
        getCategories().add("VEHICLES");
        getCategories().add("OTHERS");
    }

    public void handleFileUpload(FileUploadEvent event) throws InvalidFileTypeException {
        try {
            String newFilePath = System.getProperty("user.dir").replace("/config", "/docroot/") + event.getFile().getFileName();

            if (!newFilePath.endsWith(".jpg") && !newFilePath.endsWith(".jpeg") && !newFilePath.endsWith(".png")) {
                throw new InvalidFileTypeException("Invalid file type uploaded; only accept jpg jpeg png");
            }

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
            String absolutePath = "http://localhost:8080/" + event.getFile().getFileName();
            newListing.getImages().add(absolutePath);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        } catch (InvalidFileTypeException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));

        }
    }

    public void createNewListing(ActionEvent event) {
        try {
            CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
            newListing.setCustomer(c);
            c.getListingList().add(newListing);
            ListingEntity l = listingSessionBeanLocal.createListing(newListing);
            listings.add(l);
            filteredListings.add(l);
            newListing = new ListingEntity();
            newListing.setListingAvailable(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing created successfully (listing ID: " + l.getListingId() + ")", null));
        } catch (CreateListingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new listing: " + ex.getMessage(), null));
        }
    }

    public void redirectListing(ActionEvent event) {
        
        
        long listingIdToView = (long) event.getComponent().getAttributes().get("listingIdToView");
        System.out.println("i reached redirect listing " + listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ViewListing.xhtml");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteListing(javax.faces.event.ActionEvent event) {
        ListingEntity listingToDelete = (ListingEntity) event.getComponent().getAttributes().get("listingToDelete");

        try {
            listingSessionBeanLocal.deleteListing(listingToDelete.getListingId());
        } catch (InvalidListingException ex) {
            Logger.getLogger(ViewAllListingsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


}
