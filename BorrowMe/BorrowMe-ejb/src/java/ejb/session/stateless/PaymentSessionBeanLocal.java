/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PaymentEntity;
import java.util.List;
import util.exception.InvalidPaymentException;

public interface PaymentSessionBeanLocal {

    public PaymentEntity createPayment(PaymentEntity payment);

    public List<PaymentEntity> retrievePaymentList();

    public PaymentEntity retrievePaymentById(Long paymentId) throws InvalidPaymentException;
    
}
