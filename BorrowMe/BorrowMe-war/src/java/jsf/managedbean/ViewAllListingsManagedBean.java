package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.enumeration.CategoryEnum;
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

    public StreamedContent getImage(ListingEntity listing) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BufferedImage img = ImageIO.read(context.getExternalContext()
                    .getResourceAsStream(listing.getFirstImage()));
            if (img == null) {
                System.out.println("bufferedimage is null???");
            }
            int w = img.getWidth(null);
            int h = img.getHeight(null);

            // image is scaled two times at run time
            int scale = 2;

            BufferedImage bi = new BufferedImage(w * scale, h * scale,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();

            g.drawImage(img, 10, 10, w * scale, h * scale, null);

            ImageIO.write(bi, "png", bos);

            return new DefaultStreamedContent(new ByteArrayInputStream(
                    bos.toByteArray()), "image/png");

        }
    }

    public void handleFileUpload(FileUploadEvent event) throws InvalidFileTypeException {
        try {
            String newFilePath = System.getProperty("user.dir").replace("/config", "/docroot/") + event.getFile().getFileName();

            if (!newFilePath.endsWith(".jpg") && !newFilePath.endsWith(".jpeg") && !newFilePath.endsWith(".png")) {
                throw new InvalidFileTypeException("invalid file type uploaded; only accept jpg jpeg png");
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
            newListing.getImages().add(newFilePath);
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
            newListing.setCustomerEntity(c);
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
