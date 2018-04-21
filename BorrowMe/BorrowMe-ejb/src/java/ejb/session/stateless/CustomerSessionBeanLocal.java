/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author User
 */
@Local
public interface CustomerSessionBeanLocal {

    public long createCustomer(CustomerEntity customer) throws CustomerExistException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public CustomerEntity updateCustomer(CustomerEntity customer) throws CustomerNotFoundException;

    public CustomerEntity retrieveCustomerByCustomerId(Long custId) throws CustomerNotFoundException;

    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public List<CustomerEntity> retrieveAllCustomers();

    public Boolean doLogin(String username, String password) throws CustomerNotFoundException;

    public CustomerEntity changePassword(CustomerEntity customer) throws CustomerNotFoundException;
    
}
