/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.FeedbackSessionBeanLocal;
import entity.FeedbackEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.FeedbackExistException;
import util.exception.FeedbackNotFoundException;
import ws.restful.datamodel.Feedback.CreateFeedbackReq;
import ws.restful.datamodel.Feedback.CreateFeedbackRsp;
import ws.restful.datamodel.Feedback.ErrorRsp;
import ws.restful.datamodel.Feedback.RetrieveFeedbackRsp;
import ws.restful.datamodel.Feedback.UpdateFeedbackReq;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("feedback")
public class FeedbackResource {

    FeedbackSessionBeanLocal feedbackSessionBean = lookupFeedbackSessionBeanLocal();

    @Context
    private UriInfo context;

    public FeedbackResource() {
        this.feedbackSessionBean = lookupFeedbackSessionBeanLocal();
    }

    @Path("{reviewerId}/{revieweeId}/{listingId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFeedback(JAXBElement<CreateFeedbackReq> jaxbCreateFeedbackReq,
            @PathParam("reviewerId")Long reviewerId,
            @PathParam("revieweeId")Long revieweeId,
            @PathParam("listingId")Long listingId) {
        if((jaxbCreateFeedbackReq != null) && (jaxbCreateFeedbackReq.getValue() != null)) {
            try {
                CreateFeedbackReq createFeedbackReq = jaxbCreateFeedbackReq.getValue();
                Long id = feedbackSessionBean.createFeedback(createFeedbackReq.getFeedback());
                FeedbackEntity feedback = feedbackSessionBean.retrieveFeedbackById(id);
                CreateFeedbackRsp createFeedbackRsp = new CreateFeedbackRsp(feedback);
                return Response.status(Response.Status.OK).entity(createFeedbackRsp).build();
            } catch (FeedbackExistException | FeedbackNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            } 
        }
            else {
                ErrorRsp errorRsp = new ErrorRsp("Invalid create feedback request");
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();                    
            }
    }
    

    @Path("feedbackAsBorrower")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFeedback(JAXBElement<UpdateFeedbackReq> jaxbUpdateFeedbackReq) {
        if ((jaxbUpdateFeedbackReq != null) && (jaxbUpdateFeedbackReq.getValue() != null)) {
            try {
                UpdateFeedbackReq updateFeedbackReq = jaxbUpdateFeedbackReq.getValue();

                FeedbackEntity updated = feedbackSessionBean.updateFeedback(updateFeedbackReq.getFeedback());

                RetrieveFeedbackRsp retrieveFeedbackRsp = new RetrieveFeedbackRsp(feedbackSessionBean.retrieveFeedbackById(updated.getFeedbackId()));

                return Response.status(Response.Status.OK).entity(retrieveFeedbackRsp).build();
            } catch (FeedbackNotFoundException ex) {
                ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp("Invalid update feedback request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
//    @Path("feedbackAsLender")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateFeedbackAsLender(JAXBElement<UpdateFeedbackReq> jaxbUpdateFeedbackReq) {
//        if ((jaxbUpdateFeedbackReq != null) && (jaxbUpdateFeedbackReq.getValue() != null)) {
//            try {
//                UpdateFeedbackReq updateFeedbackReq = jaxbUpdateFeedbackReq.getValue();
//
//                FeedbackEntity updated = feedbackSessionBean.updateFeedbackAsLender(updateFeedbackReq.getFeedback());
//
//                RetrieveFeedbackRsp retrieveFeedbackRsp = new RetrieveFeedbackRsp(feedbackSessionBean.retrieveFeedback(updated.getFeedbackId()));
//
//                return Response.status(Response.Status.OK).entity(retrieveFeedbackRsp).build();
//            } catch (FeedbackNotFoundException ex) {
//                ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp(ex.getMessage());
//
//                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
//            } catch (Exception ex) {
//                ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp(ex.getMessage());
//
//                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
//            }
//        } else {
//            ws.restful.datamodel.Feedback.ErrorRsp errorRsp = new ws.restful.datamodel.Feedback.ErrorRsp("Invalid update feedback request");
//
//            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
//        }
//    }

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
