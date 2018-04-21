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
public class PaymentNotFoundException extends Exception{
    public PaymentNotFoundException() {
    }

    public PaymentNotFoundException(String msg) {
        super(msg);
    }
}
