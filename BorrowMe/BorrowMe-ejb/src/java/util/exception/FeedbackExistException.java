/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author katrina
 */
public class FeedbackExistException extends Exception {

    /**
     * Creates a new instance of <code>FeedbackExistException</code> without
     * detail message.
     */
    public FeedbackExistException() {
    }

    /**
     * Constructs an instance of <code>FeedbackExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FeedbackExistException(String msg) {
        super(msg);
    }
}
