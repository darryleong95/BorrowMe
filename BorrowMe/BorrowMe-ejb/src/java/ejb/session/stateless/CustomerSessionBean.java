package ejb.session.stateless;

import entity.CustomerEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

@Stateless
@Local(CustomerSessionBeanLocal.class)
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public long createCustomer(CustomerEntity customer) throws CustomerExistException {
        try {
            em.persist(customer);
            em.flush();
            em.refresh(customer);
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CustomerExistException("Customer already exists!\n");
            }
        }
        return customer.getCustomerId();
    }

    @Override
    public List<CustomerEntity> retrieveAllCustomers() {
        Query query = em.createNamedQuery("selectAllCustomers");
        return query.getResultList();
    }
    
    @Override
    public CustomerEntity changePassword(CustomerEntity customer) throws CustomerNotFoundException {
        if (customer.getCustomerId()!= null) {
            CustomerEntity customerToUpdate = retrieveCustomerByCustomerId((Long) customer.getCustomerId());
            customerToUpdate.setPassword(customer.getPassword());
            System.out.println("***********************************Password has been changed on the session bean***********************************");
            return retrieveCustomerByCustomerId((Long) customer.getCustomerId());
        } else {
            throw new CustomerNotFoundException("ID not provided for customer to be updated");
        }
    }
    
    @Override
    public CustomerEntity updateCustomer(CustomerEntity customer) throws CustomerNotFoundException {
        if (customer.getCustomerId()!= null) {
            CustomerEntity customerToUpdate = retrieveCustomerByCustomerId((Long) customer.getCustomerId());
            customerToUpdate.setContactNo(customer.getContactNo());
            customerToUpdate.setFirstName(customer.getFirstName());
            customerToUpdate.setLastName(customer.getLastName());
            customerToUpdate.setPassword(customer.getPassword());
            customerToUpdate.setUsername(customer.getUsername());
            customerToUpdate.setIdentificationNo(customer.getIdentificationNo());
            System.out.println("***********************************Check***********************************");
            return retrieveCustomerByCustomerId((Long) customer.getCustomerId());
        } else {
            throw new CustomerNotFoundException("ID not provided for customer to be updated");
        }
    }

    @Override
    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (CustomerEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }

    @Override
    public CustomerEntity retrieveCustomerByCustomerId(Long custId) throws CustomerNotFoundException {
        CustomerEntity customer = em.find(CustomerEntity.class, custId);
        if (customer != null) {
            customer.getListingList().size();
            customer.getFeedbacksGiven().size();
            customer.getFeedbacksReceived().size();
            customer.getRequestList().size();
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer Id " + custId + " does not exist!");
        }
    }

    @Override
    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            CustomerEntity customer = retrieveCustomerByUsername(username);
            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public Boolean doLogin(String username, String password) throws CustomerNotFoundException {
        try {
            CustomerEntity customer = retrieveCustomerByUsername(username);
            System.out.println("PRINTS THIS");
            if (customer.getPassword().equals(password)) {
                System.out.println("CAME THROUGH");
                return true;
            } else {
                return false;
            }
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }
    
    public CustomerEntity applyPremium(long customerId) throws CustomerNotFoundException{
        CustomerEntity customer = retrieveCustomerByCustomerId(customerId);
        
        customer.setCustomerType("PREMIUM");
        
        return customer;
    }

}
