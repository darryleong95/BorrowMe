package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FeedbackEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    @Column(nullable = false)
    private Integer rating;
    @Column(nullable = false, length = 1000)
    private String review;
    
    @ManyToOne
    private RequestEntity requestEntity;
    
    @ManyToOne
    private ListingEntity listing;
    
    @ManyToOne
    private CustomerEntity reviewer;
    
    @ManyToOne
    private CustomerEntity reviewee;
    
    public FeedbackEntity() {
    }

    public FeedbackEntity(Integer rating, String review, RequestEntity requestEntity, CustomerEntity reviewer, CustomerEntity reviewee, ListingEntity listing) {
        this.rating = rating;
        this.review = review;
        this.requestEntity = requestEntity;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.listing = listing;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getFeedbackId() != null ? getFeedbackId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeedbackEntity)) {
            return false;
        }
        FeedbackEntity other = (FeedbackEntity) object;
        if ((this.getFeedbackId() == null && other.getFeedbackId() != null) || (this.getFeedbackId() != null && !this.feedbackId.equals(other.feedbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FeedbackEntity[ id=" + getFeedbackId() + " ]";
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public RequestEntity getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public CustomerEntity getReviewer() {
        return reviewer;
    }

    public void setReviewer(CustomerEntity reviewer) {
        this.reviewer = reviewer;
    }

    public CustomerEntity getReviewee() {
        return reviewee;
    }

    public void setReviewee(CustomerEntity reviewee) {
        this.reviewee = reviewee;
    }

    public ListingEntity getListing() {
        return listing;
    }

    public void setListing(ListingEntity listing) {
        this.listing = listing;
    }
    
}
