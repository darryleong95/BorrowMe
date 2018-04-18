package ejb.session.stateless;

import entity.PaymentEntity;
import entity.RequestEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public PaymentEntity makePayment(Long requestId) throws PaymentNotFoundException {
        try {
            System.out.println("1");
            RequestEntity request = requestSessionBeanLocal.retrieveRequestByID(requestId);
            System.out.println("2");
            PaymentEntity payment = request.getPaymentEntity();
            System.out.println("3");
            Double amount = request.getListingEntity().getCostPerDay();
            System.out.println("4");
            int noDays = request.getNoOfDays();
            Double total = (Double) (noDays * amount);
            request.setPayment(true);
            System.out.println("Number of Days: " + noDays);
            System.out.println("Cost per Day: " + amount);
            payment.setTotalAmount(total);
            payment.setStatus(true);
            System.out.println("Successfully");
            return retrievePayment(payment.getPaymentEntityId());
        } catch (RequestNotFoundException ex) {
            Logger.getLogger(PaymentSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
