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
public class CreateRequestException extends Exception {

    /**
     * Creates a new instance of <code>CreateRequestException</code> without
     * detail message.
     */
    public CreateRequestException() {
    }

    /**
     * Constructs an instance of <code>CreateRequestException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateRequestException(String msg) {
        super(msg);
    }
}
