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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class RequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestEntityId;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(nullable = false)
    private Integer noOfDays;
    @Column(nullable = false)
    private Boolean payment;
    @Column(nullable = false)
    private Boolean acknowledged;
    @Column(nullable = false)
    private Boolean accepted;
    @Column(nullable = false)
    private Boolean overdue;
    @Column(nullable = false)
    private String message;
    
    @ManyToOne
    private ListingEntity listingEntity;

    @ManyToOne
    private CustomerEntity customerEntity;
    
    @OneToOne
    private PaymentEntity paymentEntity;

    
    public RequestEntity() {
        acknowledged = false;
        accepted = false;
        payment = false;
        overdue = false;
    }

    public RequestEntity(Long requestEntityId, Date startDate, Date endDate, Integer noOfDays, Boolean payment, Boolean accepted, Boolean overdue, ListingEntity listingEntity, CustomerEntity customerEntity) {
        this.requestEntityId = requestEntityId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.noOfDays = noOfDays;
        this.payment = false; //defauly false
        this.accepted = false; //default false
        this.overdue = false; //default false
        this.listingEntity = listingEntity;
        this.customerEntity = customerEntity;
    }
    
    public ListingEntity getListingEntity() {
        return listingEntity;
    }

    public void setListingEntity(ListingEntity listingEntity) {
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

    public Long getRequestEntityId() {
        return requestEntityId;
    }

    public void setRequestEntityId(Long requestEntityId) {
        this.requestEntityId = requestEntityId;
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
        this.setNoOfDays((Integer) noOfDays);
    }

    public Boolean isPayment() {
        return getPayment();
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
    }

    public Boolean isAccepted() {
        return getAccepted();
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isOverdue() {
        return getOverdue();
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestEntityId != null ? requestEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestEntity)) {
            return false;
        }
        RequestEntity other = (RequestEntity) object;
        if ((this.requestEntityId == null && other.requestEntityId != null) || (this.requestEntityId != null && !this.requestEntityId.equals(other.requestEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RequestEntity[ id=" + requestEntityId + " ]";
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Boolean getPayment() {
        return payment;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(Boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

}
