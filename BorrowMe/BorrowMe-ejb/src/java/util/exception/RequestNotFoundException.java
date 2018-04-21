/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author josh
 */
public class RequestNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>RequestNotFoundException</code> without
     * detail message.
     */
    public RequestNotFoundException() {
    }

    /**
     * Constructs an instance of <code>RequestNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RequestNotFoundException(String msg) {
        super(msg);
    }
}