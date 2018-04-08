package ejb.session.stateless;

import entity.PaymentEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreatePaymentException;
import util.exception.PaymentNotFoundException;

@Stateless
public class PaymentSessionBean implements PaymentSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    //This is supposed to be created upon accepting of request
    @Override
    public Long createPayment(PaymentEntity payment) throws CreatePaymentException {
        em.persist(payment);
        em.flush();
        payment.setStatus(false); //set to false by default
        return payment.getId();
    }

    @Override
    public PaymentEntity retrievePayment(Long id) throws PaymentNotFoundException {
        PaymentEntity payment = em.find(PaymentEntity.class, id);
        if (payment != null) {
            return payment;
        } else {
            throw new PaymentNotFoundException("Payment " + id + " does not exist");
        }
    }

    @Override
    public PaymentEntity makePayment(Long id, Double paymentAmount) throws PaymentNotFoundException {
        PaymentEntity payment = retrievePayment(id);
        payment.setTotalAmount(paymentAmount);
        payment.setStatus(true);
        return retrievePayment(payment.getId());
    }

}
