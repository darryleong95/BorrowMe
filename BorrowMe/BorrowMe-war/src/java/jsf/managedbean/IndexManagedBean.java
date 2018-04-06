/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fabian
 */
@Named(value = "indexManagedBean")
@RequestScoped
public class IndexManagedBean {

    /**
     * Creates a new instance of IndexManagedBean
     */
    public IndexManagedBean() {
    }
    
}
