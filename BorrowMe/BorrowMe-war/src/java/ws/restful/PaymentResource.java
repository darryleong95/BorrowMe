package ws.restful;
//POJO CLASS
import ejb.session.stateless.PaymentSessionBeanLocal;
import entity.PaymentEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import ws.restful.datamodel.Payment.CreatePaymentReq;
import ws.restful.datamodel.Payment.CreatePaymentRsp;
import ws.restful.datamodel.Payment.ErrorRsp;
import ws.restful.datamodel.Payment.RetrieveAllPaymentsRsp;

@Path("Payment") //demarcate the URI to identify resource

public class PaymentResource
{

    PaymentSessionBeanLocal paymentSessionBeanLocal = lookupPaymentSessionBeanLocal();
    @Context
    private UriInfo context;
    
    
 
    public PaymentResource() 
    {
        paymentSessionBeanLocal = lookupPaymentSessionBeanLocal();
    }


    @Path("retrieveAllPayments") //-> has to specify the method because there are 2 GET Methods
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPayments()
    {
        try
        {
            RetrieveAllPaymentsRsp retrieveAllPaymentsRsp = new RetrieveAllPaymentsRsp(paymentSessionBeanLocal.retrievePaymentList());
            //RetrieveAllPaymentsRsp -> basically is a trivial class, its to wrap object up such that when the JSON Obj goes back to the JS side, the JSON string will be nicely formatted 
            //List<Payment>
            //Java class NOT and Entity class
            //In order for the JAXB to understand how to convert this to a JSON/XML,have to annotate with XMLRootElement.
            return Response.status(Status.OK).entity(retrieveAllPaymentsRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(JAXBElement<CreatePaymentReq> jaxbCreatePaymentReq)
    {
        if((jaxbCreatePaymentReq != null) && (jaxbCreatePaymentReq.getValue() != null))
        {
            try
            {
                CreatePaymentReq createPaymentReq = jaxbCreatePaymentReq.getValue();
                
                PaymentEntity paymentEntity = paymentSessionBeanLocal.createPayment(createPaymentReq.getPayment());
                CreatePaymentRsp createPaymentRsp = new CreatePaymentRsp(paymentEntity);
                
                return Response.status(Response.Status.OK).entity(createPaymentRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create payment request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
 
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
