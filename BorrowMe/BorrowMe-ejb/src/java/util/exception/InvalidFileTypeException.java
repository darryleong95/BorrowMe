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
public class InvalidFileTypeException extends Exception {

    /**
     * Creates a new instance of <code>InvalidFileTypeException</code> without
     * detail message.
     */
    public InvalidFileTypeException() {
    }

    /**
     * Constructs an instance of <code>InvalidFileTypeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidFileTypeException(String msg) {
        super(msg);
    }
}
