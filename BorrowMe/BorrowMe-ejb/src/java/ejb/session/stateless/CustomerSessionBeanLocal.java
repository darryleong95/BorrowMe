/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
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

    public CustomerEntity createCustomer(CustomerEntity customer) throws CustomerExistException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public void updateCustomer(CustomerEntity customer);

    public CustomerEntity retrieveCustomerByCustomerId(Long custId) throws CustomerNotFoundException;

    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
