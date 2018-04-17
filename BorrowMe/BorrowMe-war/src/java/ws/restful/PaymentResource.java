/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.PaymentSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.CreatePaymentException;
import ws.restful.datamodel.Payment.CreatePaymentReq;
import ws.restful.datamodel.Payment.CreatePaymentRsp;
import ws.restful.datamodel.Payment.ErrorRsp;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("payment")
public class PaymentResource {

    PaymentSessionBeanLocal paymentSessionBean = lookupPaymentSessionBeanLocal();

    @Context
    private UriInfo context;

    public PaymentResource() {
        this.paymentSessionBean = lookupPaymentSessionBeanLocal();
    }

    //create payment 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(JAXBElement<CreatePaymentReq> jaxbCreatePaymentReq) {
        if ((jaxbCreatePaymentReq != null) && (jaxbCreatePaymentReq.getValue() != null)) {
            try {
                CreatePaymentReq createPaymentReq = jaxbCreatePaymentReq.getValue();
                Long id = paymentSessionBean.createPayment(createPaymentReq.getPayment());
                CreatePaymentRsp createPaymentRsp = new CreatePaymentRsp(id);
                return Response.status(Response.Status.OK).entity(createPaymentRsp).build();
            } catch (CreatePaymentException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create payment request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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

}
