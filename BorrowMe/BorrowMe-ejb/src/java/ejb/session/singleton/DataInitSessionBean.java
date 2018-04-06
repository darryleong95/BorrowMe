package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;

@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            System.out.println("**********INITIALISING DATA**********");
            CustomerEntity cust = customerSessionBean.retrieveCustomerByUsername("testerAccount");
        } catch (CustomerNotFoundException ex) {
            try {
                CustomerEntity customer = new CustomerEntity("Darryl", "Leong", "testerAccount", "password", "S9505342D", "91919177", "darryleong95@gmail.com");
                customerSessionBean.createCustomer(customer);
            } catch (CustomerExistException e) {
                e.printStackTrace();
            }
        }
    }
}
