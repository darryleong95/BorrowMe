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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.CustomerExistException;

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
    public CustomerEntity createCustomer (CustomerEntity customer) throws CustomerExistException{
        try{
        em.persist(customer);
        em.flush();
        em.refresh(customer);
        }catch(PersistenceException ex){
            if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CustomerExistException("Customer with same Identification number already exists!\n");
            }
        }
        return customer;
    }
    
}
