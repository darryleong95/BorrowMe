package ejb.session.stateless;

import entity.CustomerEntity;
import entity.ListingEntity;
import entity.PaymentEntity;
import entity.RequestEntity;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreatePaymentException;
import util.exception.CreateRequestException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidListingException;
import util.exception.PaymentNotFoundException;
import util.exception.RequestNotFoundException;

@Stateless
@Local(RequestSessionBeanLocal.class)
public class RequestSessionBean implements RequestSessionBeanLocal {

    @EJB(name = "PaymentSessionBeanLocal")
    private PaymentSessionBeanLocal paymentSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public RequestEntity createRequest(RequestEntity newRequest) throws CreateRequestException {

        try {
            if (newRequest.getNoOfDays() <= 0) {
                throw new CreateRequestException("Must rent for >= 1 days!");
            }
            System.out.println("Customer ID of newRequest at session bean: " + newRequest.getCustomerEntity().getCustomerId());
            CustomerEntity c = null;
            try {
                c = customerSessionBeanLocal.retrieveCustomerByCustomerId(newRequest.getCustomerEntity().getCustomerId());
            } catch (CustomerNotFoundException ex) {
                System.out.println("CUSTOMER NOT FOUND SESSION BEAN WHILE CREATING REQUEST");
            }

            ListingEntity l = null;
            try {
                l = listingSessionBeanLocal.retrieveListingById(newRequest.getListingEntity().getListingId());
            } catch (InvalidListingException ex) {
                System.out.println("Listing NOT FOUND SESSION BEAN WHILE CREATING REQUEST");
            }
            List<RequestEntity> requests = l.getRequestList();
            System.out.println(l.getRequestList().size());
            Date newStartDate = newRequest.getStartDate();
            Date newEndDate = newRequest.getEndDate();

            for (RequestEntity r : requests) {
                System.out.println("IM INSIDE THE LIST OF REQUESTS TO CHECK AGAINST");
                // if (r.isAccepted()) {
                Date otherStartDate = r.getStartDate();
                Date otherEndDate = r.getEndDate();
                System.out.println(otherStartDate);
                if (newStartDate.compareTo(otherStartDate) < 0) {
                    System.out.println("outerloop");
                    if (newEndDate.compareTo(otherStartDate) >= 0) {
                        System.out.println("inner loop, crashed, gonna die");
                        throw new CreateRequestException("Item unavailable for selected period!");
                    }
                }
                //}
            }

            c.getRequestList().add(newRequest);
            newRequest.setCustomerEntity(c);

            l.getRequestList().add(newRequest);
            newRequest.setListingEntity(l);
            em.persist(newRequest);
//            em.merge(c);
//            em.merge(l);
            em.flush();
            em.refresh(newRequest);
            return newRequest;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CreateRequestException("Listing with same name already exists");
            } else {
                throw new CreateRequestException("An unexpected error has occurred: " + ex.getMessage());
            }
        } //catch (Exception ex) {
//            throw new CreateRequestException("An unexpected error has occurred: " + ex.getMessage());
//        }

    }
    
        @Override
        public RequestEntity createRequestAPI(RequestEntity rq, Long requesterId, Long listingId, Date startDate, Date endDate) throws CreateRequestException{
        try {
            CustomerEntity customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(requesterId);
            
            ListingEntity listing = listingSessionBeanLocal.retrieveListingById(listingId);
            
            System.out.println("Listing Entity: "  + listing.getListingTitle());
            
            rq.setCustomerEntity(customer);
            rq.setListingEntity(listing);
            rq.setStartDate(startDate);
            rq.setEndDate(endDate);
            Long noDays = endDate.getTime() - startDate.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(noDays, TimeUnit.MILLISECONDS));
            int days = (int) TimeUnit.DAYS.convert(noDays, TimeUnit.MILLISECONDS);
            rq.setNoOfDays(days);
            em.persist(rq);   
            em.flush();
            em.refresh(rq);
            
            return rq;
        } catch (CustomerNotFoundException | InvalidListingException ex) {
            throw new CreateRequestException("An unexpected error has occurred: " + ex.getMessage());
        }
    }

    @Override
    public RequestEntity updateRequest(RequestEntity request) {
        try {
            if (request.getAccepted()) {
                PaymentEntity payment = new PaymentEntity();
                payment.setRequestEntity(request);
                payment.setListingEntity(listingSessionBeanLocal.retrieveListingById(request.getListingEntity().getListingId()));
                payment.setStatus(false);
                payment.setTotalAmount(request.getNoOfDays() * request.getListingEntity().getCostPerDay());
                long id = paymentSessionBeanLocal.createPayment(payment);
                System.out.println("payment id made:" + id);
                request.setAccepted(true);
                request.setPaymentEntity(paymentSessionBeanLocal.retrievePayment(id));
                em.merge(request);
            }
        } catch (CreatePaymentException ex) {
            System.out.println("Create payment exception!!!!!");
        } catch (InvalidListingException ex) {
            System.out.println("invalid listing exception!!!!!!");
        } catch (PaymentNotFoundException ex) {
            System.out.println("payment not found!!!!!!");
        }
        return request;
    }

    @Override
    public RequestEntity retrieveRequestByID(Long requestID) throws RequestNotFoundException {
        RequestEntity request = em.find(RequestEntity.class, requestID);
        if (request != null) {
            return request;
        } else {
            throw new RequestNotFoundException("Request ID: " + requestID + " does not exist!");
        }
    }
    
    @Override
    public List<RequestEntity> retrieveRequestByListingId(Long listingId) {
        try {
            ListingEntity ls = listingSessionBeanLocal.retrieveListingById(listingId);
            Query query = em.createQuery("SELECT r FROM RequestEntity r WHERE r.listingEntity = :inListingID");
            query.setParameter("inListingID", ls);
            System.out.println("**************Query successful**************");
            return query.getResultList();
        } catch (InvalidListingException ex) {
            Logger.getLogger(RequestSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<RequestEntity> retrieveRequestListByCustomerID(Long customerID
    ) {
        Query query = em.createQuery("SELECT c FROM RequestEntity c WHERE c.customerEntity.customerId = :inCustomerID");
        query.setParameter("inCustomerID", customerID);
        return query.getResultList();
    }

    @Override
    public List<RequestEntity> retrieveBorrowHistoryList(Long customerID
    ) {
        Query query = em.createQuery("SELECT c FROM RequestEntity c WHERE c.customerEntity.customerID = :inCustomerID AND c.payment = TRUE AND c.accepted = TRUE");
        query.setParameter("inCustomerID", customerID);
        return query.getResultList();
    }

}
