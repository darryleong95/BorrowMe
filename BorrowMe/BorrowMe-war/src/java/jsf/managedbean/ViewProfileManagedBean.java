/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.FeedbackSessionBeanLocal;
import entity.CustomerEntity;
import entity.FeedbackEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidFileTypeException;

/**
 *
 * @author katrina
 */
@Named(value = "viewProfileManagedBean")
@ViewScoped
public class ViewProfileManagedBean implements Serializable {

    @EJB(name = "FeedbackSessionBeanLocal")
    private FeedbackSessionBeanLocal feedbackSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private CustomerEntity selectedProfileToView;
    private Long loggedInCustomerId;

    private List<FeedbackEntity> feedbacksForCustomer;

    /**
     * Creates a new instance of ViewProfileManagedBean
     */
    public ViewProfileManagedBean() {
        feedbacksForCustomer = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        selectedProfileToView = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        System.err.println("cust" + selectedProfileToView);
    }
    
    public void updateProfile(ActionEvent event){
        try {
            System.err.println("in mtd");
            customerSessionBeanLocal.updateCustomer(selectedProfileToView);
            System.err.println("custid"+ selectedProfileToView);
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
            selectedProfileToView.getImages().add(absolutePath);
            if (selectedProfileToView.getFirstImage().equals("./images/defaultprofilepic.png")) {
                selectedProfileToView.getImages().remove(0);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        } catch (InvalidFileTypeException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));

        }
    }

    /**
     * @return the selectedProfileToView
     */
    public CustomerEntity getSelectedProfileToView() {
        return selectedProfileToView;
    }

    /**
     * @param selectedProfileToView the selectedProfileToView to set
     */
    public void setSelectedProfileToView(CustomerEntity selectedProfileToView) {
        this.selectedProfileToView = selectedProfileToView;
    }

    /**
     * @return the loggedInCustomerId
     */
    public Long getLoggedInCustomerId() {
        return loggedInCustomerId;
    }

    /**
     * @param loggedInCustomerId the loggedInCustomerId to set
     */
    public void setLoggedInCustomerId(Long loggedInCustomerId) {
        this.loggedInCustomerId = loggedInCustomerId;
    }

    /**
     * @return the feedbacksForCustomer
     */
    public List<FeedbackEntity> getFeedbacksForCustomer() {
        return feedbacksForCustomer;
    }

    /**
     * @param feedbacksForCustomer the feedbacksForCustomer to set
     */
    public void setFeedbacksForCustomer(List<FeedbackEntity> feedbacksForCustomer) {
        this.feedbacksForCustomer = feedbacksForCustomer;
    }

}
