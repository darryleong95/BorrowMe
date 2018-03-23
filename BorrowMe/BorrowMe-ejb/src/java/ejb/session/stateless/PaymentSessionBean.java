/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PaymentEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidPaymentException;

/**
 *
 * @author User
 */
@Stateless
@Local(PaymentSessionBeanLocal.class)
public class PaymentSessionBean implements PaymentSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public PaymentEntity createPayment(PaymentEntity payment) {
        em.persist(payment);
        em.flush();
        em.refresh(payment);
        return payment;
    }
    
    @Override
    public List<PaymentEntity> retrievePaymentList(){
        Query query = em.createQuery("SELECT s FROM PaymentEntity s");
        return query.getResultList();
    } 
    
    @Override
    public PaymentEntity retrievePaymentById(Long paymentId)throws InvalidPaymentException{
        PaymentEntity paymentEntity = em.find(PaymentEntity.class, paymentId);
        if(paymentEntity != null){
            return paymentEntity;
        } 
        else{
            throw new InvalidPaymentException("Invalid bid ID");
        }
    }
}
