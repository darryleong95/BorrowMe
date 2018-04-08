package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.CustomerExistException;
import util.exception.InvalidLoginCredentialException;

@Named(value = "loginManagedBean")
@ViewScoped
public class LoginManagedBean implements Serializable {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private String username;
    private String password;
    private CustomerEntity newCustomer;

    public LoginManagedBean() {
        newCustomer = new CustomerEntity();
    }

    public CustomerEntity getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(CustomerEntity newCustomer) {
        this.newCustomer = newCustomer;
    }

    public void login(ActionEvent event) throws IOException {
        try {

            System.err.println("*********** login");

            CustomerEntity currentCustomerEntity = customerSessionBeanLocal.customerLogin(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentCustomerEntity", currentCustomerEntity);
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (InvalidLoginCredentialException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public void logout(ActionEvent event) throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public void register(ActionEvent event) {
        try {
            long id = customerSessionBeanLocal.createCustomer(newCustomer);
            newCustomer = new CustomerEntity();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful! (customer ID: " + id + ")", null));
        } catch (CustomerExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Customer already exists! Error: " + ex.getMessage(), null));
        }
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

}
