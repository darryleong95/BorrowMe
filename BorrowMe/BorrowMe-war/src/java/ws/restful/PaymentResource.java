/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.PaymentSessionBeanLocal;
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.PaymentEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.PaymentNotFoundException;
import ws.restful.datamodel.Payment.MakePaymentReq;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("Payment")
public class PaymentResource {

    RequestSessionBeanLocal requestSessionBeanLocal = lookupRequestSessionBeanLocal();

    PaymentSessionBeanLocal paymentSessionBeanLocal = lookupPaymentSessionBeanLocal();

    @Context
    private UriInfo context;

    public PaymentResource() {
        this.paymentSessionBeanLocal = lookupPaymentSessionBeanLocal();
        this.requestSessionBeanLocal = lookupRequestSessionBeanLocal();
    }

    @Path("makePayment/{requestId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makePayment(@PathParam("requestId") Long requestId) {
        try {
            PaymentEntity result = paymentSessionBeanLocal.makePayment(requestId);
            System.out.println("11");
            MakePaymentReq makePaymentReq = new MakePaymentReq(result);
            return Response.status(Response.Status.OK).entity(makePaymentReq).build();
        } catch (PaymentNotFoundException ex) {
            ws.restful.datamodel.Payment.ErrorRsp errorRsp = new ws.restful.datamodel.Payment.ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    //make payment
    //retrieve all payments

    private PaymentSessionBeanLocal lookupPaymentSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PaymentSessionBeanLocal) c.lookup("java:global/BorrowMe/BorrowMe-ejb/PaymentSessionBean!ejb.session.stateless.PaymentSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RequestSessionBeanLocal lookupRequestSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RequestSessionBeanLocal) c.lookup("java:global/BorrowMe/BorrowMe-ejb/RequestSessionBean!ejb.session.stateless.RequestSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
