/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author User
 */
public class CreatePaymentException extends Exception{
    public CreatePaymentException() {
    }

    public CreatePaymentException(String msg) {
        super(msg);
    }
}
