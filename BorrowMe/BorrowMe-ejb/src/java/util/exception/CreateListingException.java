/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author fabian
 */
public class CreateListingException extends Exception {

    /**
     * Creates a new instance of <code>CreateListingException</code> without
     * detail message.
     */
    public CreateListingException() {
    }

    /**
     * Constructs an instance of <code>CreateListingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateListingException(String msg) {
        super(msg);
    }
}
