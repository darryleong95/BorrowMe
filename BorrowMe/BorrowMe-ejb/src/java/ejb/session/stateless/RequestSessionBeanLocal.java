/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RequestEntity;
import java.util.Date;
import java.util.List;
import util.exception.CreateRequestException;
import util.exception.InvalidListingException;
import util.exception.RequestNotFoundException;

public interface RequestSessionBeanLocal {

    public RequestEntity createRequest(RequestEntity request) throws CreateRequestException;

    public RequestEntity updateRequest(RequestEntity request);

    public List<RequestEntity> retrieveRequestListByCustomerID(Long customerID);

    public List<RequestEntity> retrieveBorrowHistoryList(Long customerID);

    public RequestEntity retrieveRequestByID(Long requestID) throws RequestNotFoundException;

    public RequestEntity createRequestAPI(RequestEntity rq, Long requesterId, Long listingId, String startDateStr, String endDateStr) throws CreateRequestException, InvalidListingException;

    public List<RequestEntity> retrieveRequestByListingId(Long listingId);

    public boolean checkItemAvailability(RequestEntity req);

    public RequestEntity acceptRequest(Long requestId) throws RequestNotFoundException;

    public RequestEntity openedRequest(Long requestId) throws RequestNotFoundException;

}