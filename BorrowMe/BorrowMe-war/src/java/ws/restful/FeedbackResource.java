package ws.restful;
//POJO CLASS
import ejb.session.stateless.FeedbackSessionBeanLocal;
import entity.FeedbackEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import ws.restful.datamodel.Feedback.CreateFeedbackReq;
import ws.restful.datamodel.Feedback.CreateFeedbackRsp;
import ws.restful.datamodel.Feedback.UpdateFeedbackReq;
import ws.restful.datamodel.Item.ErrorRsp;


@Path("Feedback") //demarcate the URI to identify resource

public class FeedbackResource
{

    FeedbackSessionBeanLocal feedbackSessionBeanLocal = lookupFeedbackSessionBeanLocal();
    @Context
    private UriInfo context;
    
    
 
    public FeedbackResource() 
    {
        feedbackSessionBeanLocal = lookupFeedbackSessionBeanLocal();
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFeedback(JAXBElement<CreateFeedbackReq> jaxbCreateFeedbackReq)
    {
        if((jaxbCreateFeedbackReq != null) && (jaxbCreateFeedbackReq.getValue() != null))
        {
            try
            {
                CreateFeedbackReq createFeedbackReq = jaxbCreateFeedbackReq.getValue();
                
                FeedbackEntity feedbackEntity = feedbackSessionBeanLocal.createFeedback(createFeedbackReq.getFeedback());
                CreateFeedbackRsp createFeedbackRsp = new CreateFeedbackRsp(feedbackEntity);
                
                return Response.status(Response.Status.OK).entity(createFeedbackRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create feedback request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFeedback(JAXBElement<UpdateFeedbackReq> jaxbUpdateFeedbackReq)
    {
        if((jaxbUpdateFeedbackReq != null) && (jaxbUpdateFeedbackReq.getValue() != null))
        {
            UpdateFeedbackReq updateFeedbackReq = jaxbUpdateFeedbackReq.getValue();

            feedbackSessionBeanLocal.updateFeedback(updateFeedbackReq.getFeedback());

            return Response.status(Response.Status.OK).build();
            
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update feedback request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
      
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFeedback(@QueryParam("id") Long id) //@QueryParam used instead of @Path -- identified with a ? in the URI
    {
        feedbackSessionBeanLocal.deleteFeedback(id);

        return Response.status(Status.OK).build();
    }
    

    private FeedbackSessionBeanLocal lookupFeedbackSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (FeedbackSessionBeanLocal) c.lookup("java:global/BorrowMe/BorrowMe-ejb/FeedbackSessionBean!ejb.session.stateless.FeedbackSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
