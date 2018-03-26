/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.CustomerTypeEnum;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author User
 */
@Stateless
@Local(CustomerSessionBeanLocal.class)
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) throws CustomerExistException {
        try {
            em.persist(customer);
            em.flush();
            em.refresh(customer);
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CustomerExistException("Customer with same Identification number already exists!\n");
            }
        }
        return customer;
    }

    @Override
    public void updateCustomer(CustomerEntity customer) {
        em.merge(customer);
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
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer Id " + custId + " does not exist!\n");
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
    
    public CustomerEntity applyPremium(long customerId) throws CustomerNotFoundException{
        CustomerEntity customer = retrieveCustomerByCustomerId(customerId);
        
        customer.setCustomerType(CustomerTypeEnum.PREMIUM);
        
        return customer;
    }

}
