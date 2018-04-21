package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class ListingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;
    @Column(length = 64, nullable = false)
    private String listingTitle;
    @Column(length = 1000, nullable = true) //I don't think this should be compulsory
    private String listingDescription;
    @Column(nullable = false)
    private Boolean listingAvailable;
    @Column(nullable = false)
    private Double costPerDay;
    @Column(nullable = false)
    
    private String category;
    @Column
    private List<String> images;
    
    @ManyToOne
    private CustomerEntity customerEntity;
    
    @OneToMany(mappedBy = "listingEntity")
    private List<RequestEntity> requestList;
    
    @OneToMany(mappedBy = "listingEntity")
    private List<PaymentEntity> paymentEntities;
    
    @OneToMany(mappedBy="listing")
    private List<FeedbackEntity> feedbacksOnListing;
    
 
    public ListingEntity() {
        requestList = new ArrayList<RequestEntity>();
        paymentEntities = new ArrayList<PaymentEntity>();
        feedbacksOnListing = new ArrayList<FeedbackEntity>();
        images = new ArrayList<String>();
    }

    public ListingEntity(String listingTitle, String listingDescription, Boolean listingAvailable, Double costPerDay, String category, List<String> images, CustomerEntity customerEntity, List<RequestEntity> requestList, List<PaymentEntity> paymentEntities, List<FeedbackEntity> feedbacksOnListing) {
        this.listingTitle = listingTitle;
        this.listingDescription = listingDescription;
        this.listingAvailable = listingAvailable;
        this.costPerDay = costPerDay;
        this.category = category;
        this.images = images;
        this.customerEntity = customerEntity;
        this.requestList = requestList;
        this.paymentEntities = paymentEntities;
        this.feedbacksOnListing = feedbacksOnListing;
    }

    public String getFirstImage() {
        return images.get(0);
    }
    
    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public Boolean getListingAvailable() {
        return listingAvailable;
    }

    public void setListingAvailable(Boolean listingAvailable) {
        this.listingAvailable = listingAvailable;
    }

    public Double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Double costPerDay) {
        this.costPerDay = costPerDay;
    }


    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    @XmlTransient
    public List<RequestEntity> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestEntity> requestList) {
        this.requestList = requestList;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listingId != null ? listingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListingEntity)) {
            return false;
        }
        ListingEntity other = (ListingEntity) object;
        if ((this.listingId == null && other.listingId != null) || (this.listingId != null && !this.listingId.equals(other.listingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ListingEntity[ id=" + listingId + " ]";
    }
    
    @XmlTransient
    public List<PaymentEntity> getPaymentEntities() {
        return paymentEntities;
    }

    public void setPaymentEntitys(List<PaymentEntity> paymentEntities) {
        this.paymentEntities = paymentEntities;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    @XmlTransient
    public List<FeedbackEntity> getFeedbacksOnListing() {
        return feedbacksOnListing;
    }

    public void setFeedbacksOnListing(List<FeedbackEntity> feedbacksOnListing) {
        this.feedbacksOnListing = feedbacksOnListing;
    }
    
}
