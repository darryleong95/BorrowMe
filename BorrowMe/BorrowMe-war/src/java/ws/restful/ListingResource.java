package ws.restful;
//POJO CLASS

import ejb.session.stateless.CustomerSessionBeanLocal;
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
import util.exception.CreateListingException;
import util.exception.CustomerNotFoundException;
import ws.restful.datamodel.Listing.RetrieveByCustomerRsp;
import ws.restful.datamodel.Listing.UpdateListingRsp;

@Path("Listing") //demarcate the URI to identify resource

public class ListingResource {

    CustomerSessionBeanLocal customerSessionBeanLocal = lookupCustomerSessionBeanLocal();

    ListingSessionBeanLocal listingSessionBeanLocal = lookupListingSessionBeanLocal();

    @Context
    private UriInfo context;

    public ListingResource() {
        this.listingSessionBeanLocal = lookupListingSessionBeanLocal();
        this.customerSessionBeanLocal = lookupCustomerSessionBeanLocal();
    }

    @Path("retrieveAllListings") //-> has to specify the method because there are 2 GET Methods
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllListings() {
        try {
            RetrieveAllListingsRsp retrieveAllListingsRsp = new RetrieveAllListingsRsp(listingSessionBeanLocal.retrieveListingList());
            return Response.status(Status.OK).entity(retrieveAllListingsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveByCustomerId/{listerId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response RetrieveByCustomerId(@PathParam("listerId") Long listerId) {
        try {
            RetrieveByCustomerRsp result = new RetrieveByCustomerRsp(listingSessionBeanLocal.retrieveListingByCustomerId(listerId));
            System.out.println("MADE IT THROUGH HERE");
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveListing/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveListing(@PathParam("id") Long id) {
        try {
            RetrieveListingRsp retrieveListingRsp = new RetrieveListingRsp(listingSessionBeanLocal.retrieveListingById(id));

            return Response.status(Status.OK).entity(retrieveListingRsp).build();
        } catch (InvalidListingException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(JAXBElement<CreateListingReq> jaxbCreateListingReq) {
        if ((jaxbCreateListingReq != null) && (jaxbCreateListingReq.getValue() != null)) {
            try {

                CreateListingReq createListingReq = jaxbCreateListingReq.getValue();
                System.out.println(createListingReq.getListing());
                ListingEntity newListing = listingSessionBeanLocal.createListing(createListingReq.getListing());
                CreateListingRsp result = new CreateListingRsp(newListing);
                return Response.status(Response.Status.OK).entity(result).build();
            } catch (CreateListingException ex) {
                ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp("Error creating Listing");
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            }
        } else {
            ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp("Invalid create customer request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("{listerId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(JAXBElement<UpdateListingReq> jaxbUpdateListingrReq, @PathParam("listerId") Long listerId) {
        if ((jaxbUpdateListingrReq != null) && (jaxbUpdateListingrReq.getValue() != null)) {
            try {
                UpdateListingReq updateListingReq = jaxbUpdateListingrReq.getValue();

                Boolean isLister = listingSessionBeanLocal.isLister(updateListingReq.getListing(), listerId);

                if (isLister) {
                    System.out.println(updateListingReq.getListing());
                    ListingEntity updated = listingSessionBeanLocal.updateListing(updateListingReq.getListing());
                    
                    UpdateListingRsp result = new UpdateListingRsp(updated);
                    return Response.status(Response.Status.OK).entity(result).build();
                } else {
                    return null;
                }
            } catch (InvalidListingException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");

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