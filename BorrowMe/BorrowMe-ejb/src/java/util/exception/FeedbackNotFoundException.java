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
public class FeedbackNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>FeedbackNotFoundException</code> without
     * detail message.
     */
    public FeedbackNotFoundException() {
    }

    /**
     * Constructs an instance of <code>FeedbackNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FeedbackNotFoundException(String msg) {
        super(msg);
    }
}
