/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RequestEntity;
import java.util.List;
import util.exception.RequestNotFoundException;

public interface RequestSessionBeanLocal {

    public RequestEntity createRequest(RequestEntity request);

    public RequestEntity updateRequest(RequestEntity request);

    public List<RequestEntity> retrieveRequestListByCustomerID(Long customerID);

    public List<RequestEntity> retrieveBorrowHistoryList(Long customerID);

    public RequestEntity retrieveRequestByID(Long requestID) throws RequestNotFoundException;

}