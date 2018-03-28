/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RequestEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.RequestNotFoundException;

/**
 *
 * @author josh
 */
@Stateless
@Local(RequestSessionBeanLocal.class)
public class RequestSessionBean implements RequestSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public RequestEntity createRequest(RequestEntity request) {
        em.persist(request);
        em.flush();
        em.refresh(request);
        return request;
    }
    
    @Override
    public RequestEntity updateRequest(RequestEntity request) {
        em.merge(request);
        return request;
    }
    
    @Override
    public RequestEntity retrieveRequestByID(Long requestID) throws RequestNotFoundException {
        RequestEntity request = em.find(RequestEntity.class, requestID);
        if(request != null) {
            return request;
        } else {
            throw new RequestNotFoundException("Request ID: " + requestID + " does not exist!");
        }
    }

    @Override
    public List<RequestEntity> retrieveRequestListByCustomerID(Long customerID) {
        Query query = em.createQuery("SELECT c FROM RequestEntity c WHERE c.customerEntity.customerId = :inCustomerID");
        query.setParameter("inCustomerID", customerID);
        return  query.getResultList();
    }
    
    @Override
    public List<RequestEntity> retrieveBorrowHistoryList(Long customerID) {
        Query query = em.createQuery("SELECT c FROM RequestEntity c WHERE c.customerEntity.customerID = :inCustomerID AND c.payment = TRUE AND c.accepted = TRUE");
        query.setParameter("inCustomerID", customerID);        
        return query.getResultList();
    }
    
    
}