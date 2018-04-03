/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author User
 */
@Entity
public class RequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private Integer noOfDays;
    @Column(nullable = false)
    private Boolean payment;
    @Column(nullable = false)
    private Boolean accepted;
    @Column(nullable = false)
    private Boolean overdue;

    
    @ManyToOne
    private ListingEntity listingEntity;

    @ManyToOne
    private CustomerEntity customerEntity;
    
    @OneToOne
    private PaymentEntity paymentEntity;

    
    public RequestEntity() {
    }

    public RequestEntity(Long requestId, Date startDate, Date endDate, Integer noOfDays, Boolean payment, Boolean accepted, Boolean overdue, ListingEntity listingEntity, CustomerEntity customerEntity) {
        this.requestId = requestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.noOfDays = noOfDays;
        this.payment = false; //defauly false
        this.accepted = false; //default false
        this.overdue = false; //default false
        this.listingEntity = listingEntity;
        this.customerEntity = customerEntity;
    }
    
    public ListingEntity getItemEntity() {
        return listingEntity;
    }

    public void setItemEntity(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Boolean isPayment() {
        return payment;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Long getId() {
        return requestId;
    }

    public void setId(Long requestId) {
        this.requestId = requestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestId != null ? requestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestEntity)) {
            return false;
        }
        RequestEntity other = (RequestEntity) object;
        if ((this.requestId == null && other.requestId != null) || (this.requestId != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RequestEntity[ id=" + requestId + " ]";
    }
    
}
