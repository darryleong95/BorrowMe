package ejb.session.stateless;

import entity.PaymentEntity;
import entity.RequestEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreatePaymentException;
import util.exception.PaymentNotFoundException;
import util.exception.RequestNotFoundException;

@Stateless
public class PaymentSessionBean implements PaymentSessionBeanLocal {

    @EJB(name = "RequestSessionBeanLocal")
    private RequestSessionBeanLocal requestSessionBeanLocal;

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    //This is supposed to be created upon accepting of request
    @Override
    public Long createPayment(PaymentEntity payment) throws CreatePaymentException {
        em.persist(payment);
        em.flush();
        payment.setStatus(false); //set to false by default
        return payment.getPaymentEntityId();
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
        return retrievePayment(payment.getPaymentEntityId());
    }

    @Override
    public PaymentEntity updatePayment(PaymentEntity paymentEntity) {
        PaymentEntity pe = null;
        try {
            pe = retrievePayment(paymentEntity.getPaymentEntityId());
            pe.setStatus(true);
            em.merge(pe);
            RequestEntity request = requestSessionBeanLocal.retrieveRequestByID(pe.getRequestEntity().getRequestEntityId());
            request.setPayment(true);
        } catch (PaymentNotFoundException ex) {
            System.out.println("payment not found exception at payment session bean");
        } catch (RequestNotFoundException ex) {
            System.out.println("request not found in payment session bean");
        }
        return pe;
    }

}
