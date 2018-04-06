/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.CustomerEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.CreateCustomerException;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import ws.restful.datamodel.Customer.CreateCustomerReq;
import ws.restful.datamodel.Customer.CreateCustomerRsp;
import ws.restful.datamodel.Customer.DoLoginRsp;
import ws.restful.datamodel.Customer.ErrorRsp;
import ws.restful.datamodel.Customer.RetrieveAllCustomersRsp;
import ws.restful.datamodel.Customer.RetrieveCustomerRsp;
import ws.restful.datamodel.Customer.UpdateCustomerReq;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("customer")
public class CustomerResource {

    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();

    @Context
    private UriInfo context;

    public CustomerResource() {
        this.customerSessionBean = lookupCustomerSessionBeanLocal();
    }

    @Path("retrieveAllCustomers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCustomers() {
        try {
            RetrieveAllCustomersRsp retrieveAllCustomersRsp = new RetrieveAllCustomersRsp(customerSessionBean.retrieveAllCustomers());
            return Response.status(Response.Status.OK).entity(retrieveAllCustomersRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveCustomer/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomer(@PathParam("username") String username) {
        try {
            RetrieveCustomerRsp retrieveCustomerRsp = new RetrieveCustomerRsp(customerSessionBean.retrieveCustomerByUsername(username));

            return Response.status(Response.Status.OK).entity(retrieveCustomerRsp).build();
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("doLogin/{username}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogin(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            DoLoginRsp doLoginRsp = new DoLoginRsp(customerSessionBean.doLogin(username, password));

            return Response.status(Response.Status.OK).entity(doLoginRsp).build();
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(JAXBElement<CreateCustomerReq> jaxbCreateCustomerReq) {
        if ((jaxbCreateCustomerReq != null) && (jaxbCreateCustomerReq.getValue() != null)) {
            try {
                CreateCustomerReq createCustomerReq = jaxbCreateCustomerReq.getValue();
                Long id = customerSessionBean.createCustomer(createCustomerReq.getCustomer());
                CreateCustomerRsp createCustomerRsp = new CreateCustomerRsp(id);
                return Response.status(Response.Status.OK).entity(createCustomerRsp).build();
            } catch (CustomerExistException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create customer request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(JAXBElement<UpdateCustomerReq> jaxbUpdateCustomerReq) {
        if ((jaxbUpdateCustomerReq != null) && (jaxbUpdateCustomerReq.getValue() != null)) {
            try {
                UpdateCustomerReq updateCustomerReq = jaxbUpdateCustomerReq.getValue();

                CustomerEntity updated = customerSessionBean.updateCustomer(updateCustomerReq.getCustomer());

                RetrieveCustomerRsp retrieveCustomerRsp = new RetrieveCustomerRsp(customerSessionBean.retrieveCustomerByUsername(updated.getUsername()));

                return Response.status(Response.Status.OK).entity(retrieveCustomerRsp).build();
            } catch (CustomerNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/RestfulTest/RestfulTest-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
