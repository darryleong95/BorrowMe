package ws.restful;
//POJO CLASS
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import ws.restful.datamodel.Customer.CreateCustomerReq;
import ws.restful.datamodel.Customer.CreateCustomerRsp;
import ws.restful.datamodel.Customer.UpdateCustomerReq;
import ws.restful.datamodel.Item.ErrorRsp;


@Path("Customer") //demarcate the URI to identify resource

public class CustomerResource
{

    CustomerSessionBeanLocal customerSessionBeanLocal = lookupCustomerSessionBeanLocal();
    @Context
    private UriInfo context;
    
    
 
    public CustomerResource() 
    {
        customerSessionBeanLocal = lookupCustomerSessionBeanLocal();
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(JAXBElement<CreateCustomerReq> jaxbCreateCustomerReq)
    {
        if((jaxbCreateCustomerReq != null) && (jaxbCreateCustomerReq.getValue() != null))
        {
            try
            {
                CreateCustomerReq createCustomerReq = jaxbCreateCustomerReq.getValue();
                
                CustomerEntity customerEntity = customerSessionBeanLocal.createCustomer(createCustomerReq.getCustomer());
                CreateCustomerRsp createCustomerRsp = new CreateCustomerRsp(customerEntity);
                
                return Response.status(Response.Status.OK).entity(createCustomerRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create customer request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(JAXBElement<UpdateCustomerReq> jaxbUpdateCustomerReq)
    {
        if((jaxbUpdateCustomerReq != null) && (jaxbUpdateCustomerReq.getValue() != null))
        {
            UpdateCustomerReq updateCustomerReq = jaxbUpdateCustomerReq.getValue();

            customerSessionBeanLocal.updateCustomer(updateCustomerReq.getCustomer());

            return Response.status(Response.Status.OK).build();
            
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
      

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/BorrowMe/BorrowMe-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
