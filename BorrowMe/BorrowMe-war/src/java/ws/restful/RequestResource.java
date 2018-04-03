package ws.restful;
//POJO CLASS
import ejb.session.stateless.RequestSessionBeanLocal;
import entity.RequestEntity;
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
import ws.restful.datamodel.Request.CreateRequestReq;
import ws.restful.datamodel.Request.CreateRequestRsp;
import ws.restful.datamodel.Request.UpdateRequestReq;
import ws.restful.datamodel.Listing.ErrorRsp;


@Path("Request") //demarcate the URI to identify resource

public class RequestResource
{

    RequestSessionBeanLocal requestSessionBeanLocal = lookupRequestSessionBeanLocal();
    @Context
    private UriInfo context;
    
    
 
    public RequestResource() 
    {
        requestSessionBeanLocal = lookupRequestSessionBeanLocal();
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRequest(JAXBElement<CreateRequestReq> jaxbCreateRequestReq)
    {
        if((jaxbCreateRequestReq != null) && (jaxbCreateRequestReq.getValue() != null))
        {
            try
            {
                CreateRequestReq createRequestReq = jaxbCreateRequestReq.getValue();
                
                RequestEntity requestEntity = requestSessionBeanLocal.createRequest(createRequestReq.getRequest());
                CreateRequestRsp createRequestRsp = new CreateRequestRsp(requestEntity);
                
                return Response.status(Response.Status.OK).entity(createRequestRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create request request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRequest(JAXBElement<UpdateRequestReq> jaxbUpdateRequestReq)
    {
        if((jaxbUpdateRequestReq != null) && (jaxbUpdateRequestReq.getValue() != null))
        {
            UpdateRequestReq updateRequestReq = jaxbUpdateRequestReq.getValue();

            requestSessionBeanLocal.updateRequest(updateRequestReq.getRequest());

            return Response.status(Response.Status.OK).build();
            
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update request request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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
