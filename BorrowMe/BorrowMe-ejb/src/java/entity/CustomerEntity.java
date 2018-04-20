package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

@NamedQueries({
    @NamedQuery(name = "selectAllCustomers", query = "SELECT c FROM CustomerEntity c")
})

@Entity
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(length = 32, nullable = true)
    private String firstName;
    @Column(length = 32, nullable = true)
    private String lastName;
    @Column(length = 32, nullable = false, unique = true) //Should be unique
    private String username;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 32, nullable = true, unique = true) //Should be unique
    private String identificationNo;
    @Column(length = 32, nullable = true, unique = true) //Should be unique?
    private String contactNo;
    @Column(length = 32, nullable = false, unique = true) //Should be unique
    private String email;
    @Column(nullable = true)
    private String customerType;
    @Column(nullable = true)
    private String profileImage;
    
    @OneToMany(mappedBy = "customerEntity")
    private List<RequestEntity> requestList;

    @OneToMany(mappedBy = "customerEntity")
    private List<ListingEntity> listingList;

    @OneToMany(mappedBy = "reviewer")
    private List<FeedbackEntity> feedbacksGiven;
    
    @OneToMany(mappedBy="reviewee")
    private List<FeedbackEntity> feedbacksReceived;

    public CustomerEntity() {
        requestList = new ArrayList<>();
        listingList = new ArrayList<>();
        feedbacksGiven = new ArrayList<>();
        feedbacksReceived = new ArrayList<>();
        customerType = ("ORDINARY");
        profileImage = "./images/defaultprofilepic.png";
    }

    public CustomerEntity(String firstName, String lastName, String username, String password, String identificationNo, String contactNo, String email) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.identificationNo = identificationNo;
        this.contactNo = contactNo;
        this.email = email;
    }
    
    
    
    public CustomerEntity(String firstName, String lastName, String username, String password, String identificationNo, String contactNo, String email, String customerType, String profileImage, List<RequestEntity> requestList, List<ListingEntity> listingList, List<FeedbackEntity> feedbacksGiven, List<FeedbackEntity> feedbacksReceived) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.identificationNo = identificationNo;
        this.contactNo = contactNo;
        this.email = email;
        this.customerType = customerType;
        this.profileImage = profileImage;
        this.requestList = requestList;
        this.listingList = listingList;
        this.feedbacksGiven = feedbacksGiven;
        this.feedbacksReceived = feedbacksReceived;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @XmlTransient
    public List<RequestEntity> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestEntity> requestList) {
        this.requestList = requestList;
    }
    
    @XmlTransient
    public List<ListingEntity> getListingList() {
        return listingList;
    }

    public void setListingList(List<ListingEntity> listingList) {
        this.listingList = listingList;
    }

    @XmlTransient
    public List<FeedbackEntity> getFeedbacksGiven() {
        return feedbacksGiven;
    }

    public void setFeedbackList(List<FeedbackEntity> feedbacksGiven) {
        this.feedbacksGiven = feedbacksGiven;
    }
    
     @XmlTransient
    public List<FeedbackEntity> getFeedbacksReceived() {
        return feedbacksReceived;
    }

    public void setFeedbacksReceived(List<FeedbackEntity> feedbacksReceived) {
        this.feedbacksReceived = feedbacksReceived;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerEntity)) {
            return false;
        }
        CustomerEntity other = (CustomerEntity) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerEntity[ id=" + customerId + " ]";
    }

    /**
     * @return the customerType
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * @param customerType the customerType to set
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

}
