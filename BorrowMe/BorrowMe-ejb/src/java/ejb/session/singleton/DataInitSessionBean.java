package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.CustomerEntity;
import entity.ListingEntity;
import entity.RequestEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.CategoryEnum;
import util.exception.CreateListingException;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;

@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

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
            CustomerEntity cust = customerSessionBean.retrieveCustomerByUsername("username");
            //long eg = 1;
            //List<String> images = new ArrayList<String>();
            //images.add("/Users/fabian/GlassFish_Server/glassfish/domains/domain1/docroot/defaultimage.jpg");
            //ListingEntity l = new ListingEntity("testingListing", "description eg", true, 100.0, CategoryEnum.PARTY, cust, images);
            //try { listingSessionBeanLocal.createListing(l);
            //} catch (CreateListingException ex) {
            //    System.out.println(ex.getMessage());
            //}
        } catch (CustomerNotFoundException ex) {
            try {
                
                CustomerEntity customer = new CustomerEntity("Darryl", "Leong", "username", "password", "S9505342D","darryleong95@gmail.com", "91919177");
                customerSessionBean.createCustomer(customer);
                /*
                CustomerEntity customer2 = new CustomerEntity("a", "b", "josh", "password", "c","d", "e");
                customerSessionBean.createCustomer(customer2);
                List<String>images = new  ArrayList<String>();
                ListingEntity listing = new ListingEntity("Genesis","Ragnarok", true, 14.0,)
*/
                
            } catch (CustomerExistException e) {
                e.printStackTrace();
            }
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
