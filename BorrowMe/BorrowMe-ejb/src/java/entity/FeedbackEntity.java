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
    @Column(nullable = true)
    private Integer itemRating;
    @Column(nullable = true)
    private Integer borrowerReviewLenderRating;
    @Column(nullable = true)
    private Integer lenderReviewBorrowerRating;
    @Column(length = 1000, nullable = true) // 1000 is a aribiturary number. feel free to change
    private String itemReview;
    @Column(length = 1000, nullable = true) // 1000 is a aribiturary number. feel free to change
    private String lenderReviewBorrower;
    @Column(length = 1000, nullable = true) // 1000 is a aribiturary number. feel free to change
    private String borrowerReviewLender;
    
    @ManyToOne
    private ItemEntity itemEntity;

    @ManyToOne
    private CustomerEntity customerEntity;

    public FeedbackEntity() {
    }

    public FeedbackEntity(Long feedbackId, Integer itemRating, Integer borrowerReviewLenderRating, Integer lenderReviewBorrowerRating, String borrowerReviewLender, String lenderReviewBorrower, String itemReview, ItemEntity itemEntity, CustomerEntity customerEntity) {
        this.feedbackId = feedbackId;
        this.itemRating = itemRating;
        this.borrowerReviewLenderRating = borrowerReviewLenderRating;
        this.lenderReviewBorrowerRating = lenderReviewBorrowerRating;
        this.itemReview = itemReview;
        this.borrowerReviewLender = borrowerReviewLender;
        this.lenderReviewBorrower = lenderReviewBorrower;
        this.itemEntity = itemEntity;
        this.customerEntity = customerEntity;
    }

    public Integer getItemRating() {
        return itemRating;
    }

    public void setItemRating(Integer itemRating) {
        this.itemRating = itemRating;
    }

    public Integer getBorrowerReviewLenderRating() {
        return borrowerReviewLenderRating;
    }

    public void setBorrowerReviewLenderRating(Integer borrowerReviewLenderRating) {
        this.borrowerReviewLenderRating = borrowerReviewLenderRating;
    }
    
    public Integer getLenderReviewBorrowerRating() {
        return lenderReviewBorrowerRating;
    }

    public void setLenderReviewBorrowerRating(Integer lenderReviewBorrowerRating) {
        this.lenderReviewBorrowerRating = lenderReviewBorrowerRating;
    }

    public String getItemReview() {
        return itemReview;
    }

    public void setItemReview(String itemReview) {
        this.itemReview = itemReview;
    }
    
    public String getBorrowerReviewLender() {
        return borrowerReviewLender;
    }

    public void setBorrowerReviewLender(String borrowerReviewLender) {
        this.borrowerReviewLender = borrowerReviewLender;
    }
    
    public String getLenderReviewBorrower() {
        return lenderReviewBorrower;
    }

    public void setLenderReviewBorrower(String lenderReviewBorrower) {
        this.lenderReviewBorrower = lenderReviewBorrower;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long id) {
        this.feedbackId = feedbackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackId != null ? feedbackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeedbackEntity)) {
            return false;
        }
        FeedbackEntity other = (FeedbackEntity) object;
        if ((this.feedbackId == null && other.feedbackId != null) || (this.feedbackId != null && !this.feedbackId.equals(other.feedbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FeedbackEntity[ id=" + feedbackId + " ]";
    }
    
}
