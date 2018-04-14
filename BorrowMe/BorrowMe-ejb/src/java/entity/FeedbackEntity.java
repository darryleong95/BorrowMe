/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author User
 */
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
    private CustomerEntity customerEntity;

    public FeedbackEntity() {
    }

    public FeedbackEntity(Integer rating, String review, RequestEntity requestEntity, CustomerEntity customerEntity) {
        this();
        this.rating = rating;
        this.review = review;
        this.requestEntity = requestEntity;
        this.customerEntity = customerEntity;
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

    /**
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * @return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * @return the requestEntity
     */
    public RequestEntity getRequestEntity() {
        return requestEntity;
    }

    /**
     * @param requestEntity the requestEntity to set
     */
    public void setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }

    /**
     * @return the customerEntity
     */
    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    /**
     * @param customerEntity the customerEntity to set
     */
    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    /**
     * @return the feedbackId
     */
    public Long getFeedbackId() {
        return feedbackId;
    }

    /**
     * @param feedbackId the feedbackId to set
     */
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
    
}
