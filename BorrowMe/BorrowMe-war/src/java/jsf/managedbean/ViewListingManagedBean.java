package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import util.exception.CreateRequestException;
import util.exception.InvalidFileTypeException;
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
    private int dateDiffValue;
    private List<RequestEntity> requests;
    private List<RequestEntity> filteredRequests;
    private boolean accepted;
    private ListingEntity selectedListingToUpdate;

    private List<String> categories;

    public ViewListingManagedBean() {
        listingToView = new ListingEntity();
        newRequestEntity = new RequestEntity();
        dateDiffValue = 0;
        requests = new ArrayList<RequestEntity>();
        filteredRequests = new ArrayList<RequestEntity>();
        selectedListingToUpdate = new ListingEntity();
        categories = new ArrayList<>();

    }

    @PostConstruct
    public void postConstruct() {
        setListingIdToView((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("listingIdToView"));
        getCategories().add("PARTY");
        getCategories().add("ELECTRONICS");
        getCategories().add("SPORTS");
        getCategories().add("VEHICLES");
        getCategories().add("OTHERS");
        try {
//            System.err.println("************ FLASH: " + getListingIdToView());
            setListingToView(listingSessionBeanLocal.retrieveListingById(getListingIdToView()));
            requests = listingToView.getRequestList();
            for (RequestEntity r : requests) {
                filteredRequests.add(r);
            }
            setSelectedListingToUpdate(getListingToView());
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

                setDateDiffValue((int) diffDays);

            } catch (Exception ex) {
                System.out.println("ERROR AT DATE DIFF: " + ex.getMessage());
            }
        }
    }

    public void createRequest(ActionEvent event) {
        try {
                CustomerEntity c = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
                c.getRequestList().add(newRequestEntity);
                newRequestEntity.setCustomerEntity(c); //borrower
                listingToView.getRequestList().add(newRequestEntity);
                newRequestEntity.setListingEntity(listingToView);
                newStartDate.setHours(12);
                newEndDate.setHours(12);
                newRequestEntity.setStartDate(newStartDate);
                newRequestEntity.setEndDate(newEndDate);
                newRequestEntity.setNoOfDays(Integer.valueOf(getDateDiffValue()));
                if (requestSessionBeanLocal.checkItemAvailability(newRequestEntity)) {
                RequestEntity r = requestSessionBeanLocal.createRequest(newRequestEntity);
                requests.add(r);
                filteredRequests.add(r);
                newRequestEntity = new RequestEntity();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New request created successfully (request ID: " + r.getRequestEntityId() + ")", null));
            } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Request rejected! The item is rented out for those dates", null));
            }
        } catch (CreateRequestException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new request: " + ex.getMessage(), null));
        }
    }

    public void updateListing(ActionEvent event) {
        try {
            listingSessionBeanLocal.updateListing(selectedListingToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing updated successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
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
            selectedListingToUpdate.getImages().add(absolutePath);
            if (selectedListingToUpdate.getFirstImage().equals("./images/noimage.png")) {
                selectedListingToUpdate.getImages().remove(0);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        } catch (InvalidFileTypeException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));

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

    public int getDateDiffValue() {
        return dateDiffValue;
    }

    public void setDateDiffValue(int dateDiffValue) {
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

    public ListingEntity getSelectedListingToUpdate() {
        return selectedListingToUpdate;
    }

    public void setSelectedListingToUpdate(ListingEntity selectedListingToUpdate) {
        this.selectedListingToUpdate = selectedListingToUpdate;
    }

    /**
     * @return the categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
