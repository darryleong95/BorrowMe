/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.FeedbackSessionBeanLocal;
import entity.CustomerEntity;
import entity.FeedbackEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author katrina
 */
@Named(value = "viewProfileManagedBean")
@ViewScoped
public class ViewProfileManagedBean implements Serializable {

    @EJB(name = "FeedbackSessionBeanLocal")
    private FeedbackSessionBeanLocal feedbackSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private CustomerEntity selectedProfileToView;
    private Long loggedInCustomerId;
    
    private List<FeedbackEntity> feedbacksForCustomer;

    /**
     * Creates a new instance of ViewProfileManagedBean
     */
    public ViewProfileManagedBean() {
        feedbacksForCustomer = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
       
        selectedProfileToView = (CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        System.err.println("cust" + selectedProfileToView);

    }

    /**
     * @return the selectedProfileToView
     */
    public CustomerEntity getSelectedProfileToView() {
        return selectedProfileToView;
    }

    /**
     * @param selectedProfileToView the selectedProfileToView to set
     */
    public void setSelectedProfileToView(CustomerEntity selectedProfileToView) {
        this.selectedProfileToView = selectedProfileToView;
    }

    /**
     * @return the loggedInCustomerId
     */
    public Long getLoggedInCustomerId() {
        return loggedInCustomerId;
    }

    /**
     * @param loggedInCustomerId the loggedInCustomerId to set
     */
    public void setLoggedInCustomerId(Long loggedInCustomerId) {
        this.loggedInCustomerId = loggedInCustomerId;
    }

    /**
     * @return the feedbacksForCustomer
     */
    public List<FeedbackEntity> getFeedbacksForCustomer() {
        return feedbacksForCustomer;
    }

    /**
     * @param feedbacksForCustomer the feedbacksForCustomer to set
     */
    public void setFeedbacksForCustomer(List<FeedbackEntity> feedbacksForCustomer) {
        this.feedbacksForCustomer = feedbacksForCustomer;
    }

}
