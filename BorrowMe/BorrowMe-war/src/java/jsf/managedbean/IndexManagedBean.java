package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;

@Named(value = "indexManagedBean")
@RequestScoped
public class IndexManagedBean {

    

}
