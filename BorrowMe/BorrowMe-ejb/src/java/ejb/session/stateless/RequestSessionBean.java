package ejb.session.stateless;

import entity.CustomerEntity;
import entity.ListingEntity;
import entity.PaymentEntity;
import entity.RequestEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
                if (r.isAccepted()) {
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
                }
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
    public RequestEntity createRequestAPI(RequestEntity rq, Long requesterId, Long listingId, String startDateStr, String endDateStr) throws CreateRequestException, InvalidListingException {
        try {
            Boolean allow = true;
            CustomerEntity customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(requesterId);

            ListingEntity listing = listingSessionBeanLocal.retrieveListingById(listingId);

            System.out.println("Listing Entity: " + listing.getListingTitle());
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

            //Check to see whether request was already made
            List<RequestEntity> requests = retrieveRequestByListingId(listingId);
            System.out.println("Request size: " + requests.size());
            for (int i = 0; i < requests.size(); i++) {
                Long customerId = requests.get(i).getCustomerEntity().getCustomerId();
                if (customerId == customer.getCustomerId()) {
                    //check to see whether dates overlap
                    Date existingStartDate = requests.get(i).getStartDate();
                    Date existingEndDate = requests.get(i).getEndDate();
                    Long front = existingStartDate.getTime() - endDate.getTime();
                    Long end = existingEndDate.getTime() - startDate.getTime();
                    int aes = (int) TimeUnit.DAYS.convert(front, TimeUnit.MILLISECONDS);
                    int aee = (int) TimeUnit.DAYS.convert(end, TimeUnit.MILLISECONDS);
                    System.out.println("Existing Start date: " + existingStartDate);
                    System.out.println("Existing End date: " + existingEndDate);
                    System.out.println("AES: " + aes);
                    System.out.println("AEE: " + aee);
                    if (aes >= 0 || aee <= 0) {
                    } else {
                        allow = false;
                    }
                }
            }
            if (requests.size() == 0) {
                allow = true;
            }
            if (allow) {
                Date now = new Date();
                Long noDaysStart = now.getTime() - startDate.getTime();
                Long noDaysEnd = endDate.getTime() - startDate.getTime();
                int dayStart = (int) TimeUnit.DAYS.convert(noDaysStart, TimeUnit.MILLISECONDS);
                int dayEnd = (int) TimeUnit.DAYS.convert(noDaysEnd, TimeUnit.MILLISECONDS);
                System.out.println("Current Date: " + now);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Days from current date: " + dayStart);
                System.out.println("Days inbetween: " + dayEnd);
                if (dayStart <= 0 && dayEnd > 0) {
                    rq.setCustomerEntity(customer);
                    rq.setListingEntity(listing);
                    rq.setStartDate(startDate);
                    rq.setEndDate(endDate);
                    Long noDays = endDate.getTime() - startDate.getTime();
                    System.out.println("Days: " + TimeUnit.DAYS.convert(noDays, TimeUnit.MILLISECONDS));
                    int days = (int) TimeUnit.DAYS.convert(noDays, TimeUnit.MILLISECONDS);
                    rq.setNoOfDays(days);
                    em.persist(rq);
                    em.flush();
                    em.refresh(rq);
                    return rq;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (CustomerNotFoundException ex) {
            throw new CreateRequestException("An unexpected error has occurred: " + ex.getMessage());
        } catch (ParseException ex) {
            throw new CreateRequestException("Parse exception" + ex.getMessage());
        } catch (InvalidListingException ex) {
            throw new InvalidListingException("Invalid listing exception:" + ex.getMessage());
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

                //TESTING: after accepting one, rejecting other requests that clash with it now
                ListingEntity l = request.getListingEntity();
                l = listingSessionBeanLocal.retrieveListingById(l.getListingId());
                Date otherStartDate = request.getStartDate();
                Date otherEndDate = request.getEndDate();
                for (RequestEntity r : l.getRequestList()) {
                    if (!r.getRequestEntityId().equals(request.getRequestEntityId())) {
                        System.out.println("removing any other conflicting requests");
                        Date newStartDate = r.getStartDate();
                        Date newEndDate = r.getEndDate();
                        if (newStartDate.compareTo(otherStartDate) <= 0) {
                            System.out.println("outerloop" + newStartDate);
                            if (newEndDate.compareTo(otherStartDate) >= 0) {
                                System.out.println("inner loop, crashed, gonna die");
                                System.out.println("clashed end date " + otherEndDate);
                                r = retrieveRequestByID(r.getRequestEntityId());
                                r.setAcknowledged(Boolean.TRUE);
                                r.setAccepted(Boolean.FALSE);
                                em.merge(r);
                            }
                        }
                    }
                }

            } else {
                em.merge(request);
            }
        } catch (CreatePaymentException ex) {
            System.out.println("Create payment exception!!!!!");
        } catch (InvalidListingException ex) {
            System.out.println("invalid listing exception!!!!!!");
        } catch (PaymentNotFoundException ex) {
            System.out.println("payment not found!!!!!!");
        } catch (RequestNotFoundException ex) {
            System.out.println(ex.getMessage() + " REQUEST CLASHED NOT FOUND");
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
        System.out.println("Testttttt");
        Query query = em.createQuery("SELECT r FROM RequestEntity r WHERE r.listingEntity.listingId = :inListingId");
        System.out.println("Testing");
        query.setParameter("inListingId", listingId);
        List<RequestEntity> requests = query.getResultList();
        System.out.println("Made it before the loop");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println("Made it here");
            System.out.println("Request ID of individual results: " + requests.get(i).getRequestEntityId());
            requests.get(i).getFeedbackList().size();
        }

        System.out.println("**************Query successful**************");
        return query.getResultList();
    }

    @Override
    public List<RequestEntity> retrieveRequestListByCustomerID(Long customerID) {
        Query query = em.createQuery("SELECT c FROM RequestEntity c WHERE c.customerEntity.customerId = :inCustomerID");
        query.setParameter("inCustomerID", customerID);
        return query.getResultList();
    }

    @Override
    public List<RequestEntity> retrieveBorrowHistoryList(Long customerID) {
        Query query = em.createQuery("SELECT c FROM RequestEntity c WHERE c.customerEntity.customerID = :inCustomerID AND c.payment = TRUE AND c.accepted = TRUE");
        query.setParameter("inCustomerID", customerID);
        return query.getResultList();
    }

    @Override
    public boolean checkItemAvailability(RequestEntity req) {
        Date reqStartDate = req.getStartDate();
        Date reqEndDate = req.getEndDate();
        ListingEntity listing = req.getListingEntity();
        List<Date> thisReq = getDaysBetweenDates(reqStartDate, reqEndDate);

        for (RequestEntity r : listing.getRequestList()) {
            if (r.getAccepted()) {//req accepted
                Date otherReqSD = r.getStartDate();
                Date otherReqED = r.getEndDate();

                for (Date newReqDate : thisReq) {
                    if (newReqDate.after(otherReqSD) && newReqDate.before(otherReqED)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Boolean deleteRequest(Long requestId) throws RequestNotFoundException {
        try {
            System.out.println("Request ID received: " + requestId);
            RequestEntity request = retrieveRequestByID(requestId);
            System.out.println(request.getEndDate());
            em.remove(request);
            return true;
        } catch (RequestNotFoundException ex) {
            throw new RequestNotFoundException("RequestID provided was not valid: " + ex.getMessage());
        }
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    @Override
    public RequestEntity acceptRequest(Long requestId) throws RequestNotFoundException {
        try {
            RequestEntity request = retrieveRequestByID(requestId);
            System.out.println(request.getRequestEntityId() + "***********************");

            Date start = request.getStartDate();
            Date end = request.getEndDate();
            System.out.println("Mid function flag");
            System.out.println("Listing ID: " + request.getListingEntity().getListingId());
            List<RequestEntity> requests = retrieveRequestByListingId(request.getListingEntity().getListingId());
            System.out.println("Request result size: " + requests.size());
            for (int i = 0; i < requests.size(); i++) {
                RequestEntity req = requests.get(i);
                //dates of req to reject
                Date toRejectStart = req.getStartDate();
                Date toRejectEnd = req.getEndDate();
                //check to see whether dates overlap
                System.out.println("Made it here");
                Long overlapFront = toRejectStart.getTime() - end.getTime();
                Long overlapEnd = toRejectEnd.getTime() - start.getTime();
                int aes = (int) TimeUnit.DAYS.convert(overlapFront, TimeUnit.MILLISECONDS);
                int aee = (int) TimeUnit.DAYS.convert(overlapEnd, TimeUnit.MILLISECONDS);
                System.out.println("Existing Start date: " + toRejectStart);
                System.out.println("Existing End date: " + toRejectEnd);
                System.out.println("AES: " + aes);
                System.out.println("AEE: " + aee);
                if (aes >= 0 || aee <= 0) {
                    //no overlap, don't do anything
                    System.out.println("Not Rejected Request: " + req.getRequestEntityId());
                } else {
                    //overlap exists, reject requests
                    if (req.getRequestEntityId() != request.getRequestEntityId()) {
                        System.out.println("Rejecting Request: " + req.getRequestEntityId());
                        req.setAcknowledged(true);
                        req.setAccepted(false);
                        em.merge(req);
                    }
                }
            }

            PaymentEntity payment = new PaymentEntity();
            payment.setStatus(false);
            payment.setListingEntity(request.getListingEntity());
            request.setAccepted(true);
            request.setAcknowledged(true);
            request.setPaymentEntity(payment);

            em.persist(payment);
            em.flush();
            em.refresh(payment);
            em.merge(request);
            return request;
        } catch (RequestNotFoundException ex) {
            throw new RequestNotFoundException("Request ID: " + requestId + " does not exist!");
        }
    }

    @Override
    public RequestEntity rejectRequest(Long requestId) throws RequestNotFoundException {
        try {
            RequestEntity request = retrieveRequestByID(requestId);
            System.out.println(request.getRequestEntityId() + "***********************");
            request.setAccepted(false);
            request.setAcknowledged(true);
            em.flush();
            em.merge(request);
            System.out.println("Set acknowledged to true and Accepted to false");
            return request;
        } catch (RequestNotFoundException ex) {
            throw new RequestNotFoundException("Request ID: " + requestId + " does not exist!");
        }
    }

    @Override
    public RequestEntity openedRequest(Long requestId) throws RequestNotFoundException {
        try {
            RequestEntity request = retrieveRequestByID(requestId);
            request.setIsOpened(true);
            System.out.println("**********Successfully set isOpened to: " + request.getIsOpened() + "**********");
            return request;
        } catch (RequestNotFoundException ex) {
            throw new RequestNotFoundException("Request ID: " + requestId + " does not exist!");
        }
    }
}
