package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
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
            CustomerEntity cust = customerSessionBean.retrieveCustomerByUsername("testerAccount");
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
                CustomerEntity customer = new CustomerEntity("Darryl", "Leong", "testerAccount", "password", "S9505342D", "91919177", "darryleong95@gmail.com");
                customerSessionBean.createCustomer(customer);
            } catch (CustomerExistException e) {
                e.printStackTrace();
            }
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
