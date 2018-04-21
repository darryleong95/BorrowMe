package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.FeedbackSessionBeanLocal;
import entity.CustomerEntity;
import entity.FeedbackEntity;
import entity.ListingEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidFileTypeException;

@Named(value = "viewProfileManagedBean")
@ViewScoped
public class ViewProfileManagedBean implements Serializable {

    @EJB(name = "FeedbackSessionBeanLocal")
    private FeedbackSessionBeanLocal feedbackSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private CustomerEntity selectedProfileToView;
    private CustomerEntity ownProfile;
    private Long loggedInCustomerId;
    private Long profileIdToView;

    private List<ListingEntity> customerListings;

    private List<FeedbackEntity> feedbacksForCustomer;

    public ViewProfileManagedBean() {
        feedbacksForCustomer = new ArrayList<>();
        customerListings = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {

        setOwnProfile((CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));

        setProfileIdToView((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("profileIdToView"));
        try {
            setSelectedProfileToView(customerSessionBeanLocal.retrieveCustomerByCustomerId(profileIdToView));
        } catch (CustomerNotFoundException ex) {
            System.out.println("customer not found in new session bean");
        }
        
        
        System.out.println(selectedProfileToView.getFeedbacksGiven().size() + "; " + selectedProfileToView.getFeedbacksReceived().size());
        
        //selectedProfileToView = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        customerListings = selectedProfileToView.getListingList();
        feedbacksForCustomer = selectedProfileToView.getFeedbacksReceived();
        System.err.println("feedback list" + feedbacksForCustomer);
        System.err.println("cust" + selectedProfileToView);
    }

    public void updateProfile(ActionEvent event) {
        try {
            System.err.println("in mtd");
            customerSessionBeanLocal.updateCustomer(selectedProfileToView);
            System.err.println("custid" + selectedProfileToView);
        } catch (CustomerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred while retrieving customer: " + ex.getMessage(), null));
        }
    }

    public void registerPremium(ActionEvent event) {
        try {
            selectedProfileToView.setCustomerType("PREMIUM");
            customerSessionBeanLocal.updateCustomer(selectedProfileToView);
        } catch (CustomerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred while retrieving customer: " + ex.getMessage(), null));
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws InvalidFileTypeException {
        try {
            String newFilePath = System.getProperty("user.dir").replace("/config", "/docroot/") + event.getFile().getFileName();

            if (!newFilePath.endsWith(".jpg") && !newFilePath.endsWith(".jpeg") && !newFilePath.endsWith(".png")) {
                throw new InvalidFileTypeException("invalid file type uploaded; only accept jpg jpeg png");
            }

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
            selectedProfileToView.setProfileImage(absolutePath);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        } catch (InvalidFileTypeException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
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

    public CustomerEntity getSelectedProfileToView() {
        return selectedProfileToView;
    }

    public void setSelectedProfileToView(CustomerEntity selectedProfileToView) {
        this.selectedProfileToView = selectedProfileToView;
    }

    public Long getLoggedInCustomerId() {
        return loggedInCustomerId;
    }

    public void setLoggedInCustomerId(Long loggedInCustomerId) {
        this.loggedInCustomerId = loggedInCustomerId;
    }

    public List<FeedbackEntity> getFeedbacksForCustomer() {
        return feedbacksForCustomer;
    }

    public void setFeedbacksForCustomer(List<FeedbackEntity> feedbacksForCustomer) {
        this.feedbacksForCustomer = feedbacksForCustomer;
    }

    public List<ListingEntity> getCustomerListings() {
        return customerListings;
    }

    public void setCustomerListings(List<ListingEntity> customerListings) {
        this.customerListings = customerListings;
    }

    public Long getProfileIdToView() {
        return profileIdToView;
    }

    public void setProfileIdToView(Long profileIdToView) {
        this.profileIdToView = profileIdToView;
    }

    public CustomerEntity getOwnProfile() {
        return ownProfile;
    }

    public void setOwnProfile(CustomerEntity ownProfile) {
        this.ownProfile = ownProfile;
    }

}
