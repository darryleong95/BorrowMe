/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PaymentEntity;
import javax.ejb.Local;
import util.exception.CreatePaymentException;
import util.exception.PaymentNotFoundException;

@Local
public interface PaymentSessionBeanLocal {

    public PaymentEntity retrievePayment(Long id) throws PaymentNotFoundException;

    public PaymentEntity makePayment(Long id, Double paymentAmount) throws PaymentNotFoundException;

    public Long createPayment(PaymentEntity payment) throws CreatePaymentException;
}
    