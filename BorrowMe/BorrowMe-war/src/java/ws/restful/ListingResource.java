package ws.restful;
//POJO CLASS
import entity.ListingEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import util.exception.InvalidListingException;
import ws.restful.datamodel.Listing.CreateListingReq;
import ws.restful.datamodel.Listing.CreateListingRsp;
import ws.restful.datamodel.Listing.ErrorRsp;
import ws.restful.datamodel.Listing.RetrieveAllListingsRsp;
import ws.restful.datamodel.Listing.RetrieveListingRsp;
import ws.restful.datamodel.Listing.UpdateListingReq;
import ejb.session.stateless.ListingSessionBeanLocal;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Path("Listing") //demarcate the URI to identify resource

public class ListingResource
{

    ListingSessionBeanLocal listingSessionBeanLocal = lookupListingSessionBeanLocal();
    @Context
    private UriInfo context;
    
    
 
    public ListingResource() 
    {
        listingSessionBeanLocal = lookupListingSessionBeanLocal();
    }

    
    
    @Path("retrieveAllListings") //-> has to specify the method because there are 2 GET Methods
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllListings()
    {
        try
        {
            RetrieveAllListingsRsp retrieveAllListingsRsp = new RetrieveAllListingsRsp(listingSessionBeanLocal.retrieveListingList());
            //RetrieveAllListingsRsp -> basically is a trivial class, its to wrap object up such that when the JSON Obj goes back to the JS side, the JSON string will be nicely formatted 
            //List<Listing>
            //Java class NOT and Entity class
            //In order for the JAXB to understand how to convert this to a JSON/XML,have to annotate with XMLRootElement.
            return Response.status(Status.OK).entity(retrieveAllListingsRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    
    @Path("retrieveListing/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveListing(@PathParam("id") Long id)
    {
        try
        {
            RetrieveListingRsp retrieveListingRsp = new RetrieveListingRsp(listingSessionBeanLocal.retrieveListingById(id));
            
            return Response.status(Status.OK).entity(retrieveListingRsp).build();
        }
        catch(InvalidListingException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
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
    public Response createListing(JAXBElement<CreateListingReq> jaxbCreateListingReq)
    {
        if((jaxbCreateListingReq != null) && (jaxbCreateListingReq.getValue() != null))
        {
            try
            {
                CreateListingReq createListingReq = jaxbCreateListingReq.getValue();
                
                ListingEntity listingEntity = listingSessionBeanLocal.createListing(createListingReq.getListing());
                CreateListingRsp createListingRsp = new CreateListingRsp(listingEntity);
                
                return Response.status(Response.Status.OK).entity(createListingRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create listing request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateListing(JAXBElement<UpdateListingReq> jaxbUpdateListingReq)
    {
        if((jaxbUpdateListingReq != null) && (jaxbUpdateListingReq.getValue() != null))
        {
            UpdateListingReq updateListingReq = jaxbUpdateListingReq.getValue();

            listingSessionBeanLocal.updateListing(updateListingReq.getListing());

            return Response.status(Response.Status.OK).build();
            
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update listing request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteListing(@QueryParam("id") Long id) throws InvalidListingException //@QueryParam used instead of @Path -- identified with a ? in the URI
    {
        listingSessionBeanLocal.deleteListing(id);

        return Response.status(Status.OK).build();
    }

    private ListingSessionBeanLocal lookupListingSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ListingSessionBeanLocal) c.lookup("java:global/BorrowMe/BorrowMe-ejb/ListingSessionBean!ejb.session.stateless.ListingSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}

