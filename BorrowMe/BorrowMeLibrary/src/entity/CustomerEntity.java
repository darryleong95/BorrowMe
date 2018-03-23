/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import util.Enum.CustomerTypeEnum;

/**
 *
 * @author User
 */
@Entity
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false, unique = true) //Should be unique
    private String username;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 32, nullable = false, unique = true) //Should be unique
    private String identificationNo;
    @Column(length = 32, nullable = false, unique = true) //Should be unique?
    private String contactNo;
    @Column(length = 32, nullable = false, unique = true) //Should be unique
    private String email;
    @Column(nullable = false)
    private CustomerTypeEnum customerType;
    //private boolean isSuspended; --> lol seriously KIV
    
    @OneToMany(mappedBy = "customerEntity")
    private List<AddressEntity> addressList;
    
    @OneToMany(mappedBy = "customerEntity")
    private List<RequestEntity> requestList;
        
    @OneToMany(mappedBy = "customerEntity")
    private List<ItemEntity> itemList;
    
    @OneToMany(mappedBy = "customerEntity")
    private List<FeedbackEntity> feedbackList;

    public CustomerEntity() {
        addressList = new ArrayList<>();
        requestList = new ArrayList<>();
        itemList = new ArrayList<>();
        feedbackList = new ArrayList<>();
    }

    public CustomerEntity(Long customerId, String firstName, String lastName, String username, String password, String identificationNo, String contactNo, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.identificationNo = identificationNo;
        this.contactNo = contactNo;
        this.email = email;
        this.customerType = CustomerTypeEnum.ORDINARY; //default ORDINARY until subscribed
    }
 
    public List<AddressEntity> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressEntity> addressList) {
        this.addressList = addressList;
    }

    public List<RequestEntity> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestEntity> requestList) {
        this.requestList = requestList;
    }

    public List<ItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemEntity> itemList) {
        this.itemList = itemList;
    }

    public List<FeedbackEntity> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<FeedbackEntity> feedbackList) {
        this.feedbackList = feedbackList;
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

    public CustomerTypeEnum getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerTypeEnum customerType) {
        this.customerType = customerType;
    }
    
    public Long getcustomerId() {
        return customerId;
    }

    public void setcustomerId(Long customerId) {
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
    
}