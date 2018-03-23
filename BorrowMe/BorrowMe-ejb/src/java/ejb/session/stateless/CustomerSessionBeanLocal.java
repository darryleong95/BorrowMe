/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Local;
import util.exception.CustomerExistException;

/**
 *
 * @author User
 */
@Local
public interface CustomerSessionBeanLocal {

    public CustomerEntity createCustomer(CustomerEntity customer) throws CustomerExistException;
    
}
