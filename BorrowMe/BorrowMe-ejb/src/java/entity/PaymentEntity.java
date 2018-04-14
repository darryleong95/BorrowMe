package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentEntityId;

    @Column(nullable = true)
    private Double totalAmount;
    
    @Column(nullable = false)
    private Boolean status;
    
    @OneToOne(mappedBy = "paymentEntity")
    private RequestEntity requestEntity;
    
    @ManyToOne
    private ListingEntity listingEntity;

    public PaymentEntity(){
        
    }
    
    public PaymentEntity(Double totalAmount, Boolean status){
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Boolean getStatus(){
        return status;
    }
    
    public void setStatus(Boolean status){
        this.status = status;
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getPaymentEntityId() {
        return paymentEntityId;
    }

    public void setPaymentEntityId(Long paymentEntityId) {
        this.paymentEntityId = paymentEntityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentEntityId != null ? paymentEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentEntity)) {
            return false;
        }
        PaymentEntity other = (PaymentEntity) object;
        if ((this.paymentEntityId == null && other.paymentEntityId != null) || (this.paymentEntityId != null && !this.paymentEntityId.equals(other.paymentEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Payment[ id=" + paymentEntityId + " ]";
    }

    public RequestEntity getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }

    public ListingEntity getListingEntity() {
        return listingEntity;
    }

    public void setListingEntity(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }
    
}
