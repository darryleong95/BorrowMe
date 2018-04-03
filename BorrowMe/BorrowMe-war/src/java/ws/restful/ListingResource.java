package ws.restful;
//POJO CLASS
import ejb.session.stateless.ItemSessionBeanLocal;
import entity.ItemEntity;
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
import util.exception.InvalidItemException;
import ws.restful.datamodel.Item.CreateItemReq;
import ws.restful.datamodel.Item.CreateItemRsp;
import ws.restful.datamodel.Item.ErrorRsp;
import ws.restful.datamodel.Item.RetrieveAllItemsRsp;
import ws.restful.datamodel.Item.RetrieveItemRsp;
import ws.restful.datamodel.Item.UpdateItemReq;

@Path("Item") //demarcate the URI to identify resource

public class ItemResource
{

    ItemSessionBeanLocal itemSessionBeanLocal = lookupItemSessionBeanLocal();
    @Context
    private UriInfo context;
    
    
 
    public ItemResource() 
    {
        itemSessionBeanLocal = lookupItemSessionBeanLocal();
    }

    
    
    @Path("retrieveAllItems") //-> has to specify the method because there are 2 GET Methods
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllItems()
    {
        try
        {
            RetrieveAllItemsRsp retrieveAllItemsRsp = new RetrieveAllItemsRsp(itemSessionBeanLocal.retrieveItemList());
            //RetrieveAllItemsRsp -> basically is a trivial class, its to wrap object up such that when the JSON Obj goes back to the JS side, the JSON string will be nicely formatted 
            //List<Item>
            //Java class NOT and Entity class
            //In order for the JAXB to understand how to convert this to a JSON/XML,have to annotate with XMLRootElement.
            return Response.status(Status.OK).entity(retrieveAllItemsRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    
    @Path("retrieveItem/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveItem(@PathParam("id") Long id)
    {
        try
        {
            RetrieveItemRsp retrieveItemRsp = new RetrieveItemRsp(itemSessionBeanLocal.retrieveItemById(id));
            
            return Response.status(Status.OK).entity(retrieveItemRsp).build();
        }
        catch(InvalidItemException ex)
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
    public Response createItem(JAXBElement<CreateItemReq> jaxbCreateItemReq)
    {
        if((jaxbCreateItemReq != null) && (jaxbCreateItemReq.getValue() != null))
        {
            try
            {
                CreateItemReq createItemReq = jaxbCreateItemReq.getValue();
                
                ItemEntity itemEntity = itemSessionBeanLocal.createItem(createItemReq.getItem());
                CreateItemRsp createItemRsp = new CreateItemRsp(itemEntity);
                
                return Response.status(Response.Status.OK).entity(createItemRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create item request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(JAXBElement<UpdateItemReq> jaxbUpdateItemReq)
    {
        if((jaxbUpdateItemReq != null) && (jaxbUpdateItemReq.getValue() != null))
        {
            UpdateItemReq updateItemReq = jaxbUpdateItemReq.getValue();

            itemSessionBeanLocal.updateItem(updateItemReq.getItem());

            return Response.status(Response.Status.OK).build();
            
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update item request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@QueryParam("id") Long id) throws InvalidItemException //@QueryParam used instead of @Path -- identified with a ? in the URI
    {
        itemSessionBeanLocal.deleteItem(id);

        return Response.status(Status.OK).build();
    }

    private ItemSessionBeanLocal lookupItemSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ItemSessionBeanLocal) c.lookup("java:global/BorrowMe/BorrowMe-ejb/ItemSessionBean!ejb.session.stateless.ItemSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
