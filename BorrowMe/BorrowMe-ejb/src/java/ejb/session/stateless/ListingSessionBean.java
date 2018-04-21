package ejb.session.stateless;

import entity.CustomerEntity;
import entity.ListingEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateListingException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidListingException;

@Stateless
@Local(ListingSessionBeanLocal.class)
public class ListingSessionBean implements ListingSessionBeanLocal {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public ListingEntity createListing(ListingEntity newListing) throws CreateListingException {
        try {
            if (newListing.getImages().isEmpty()) {
                System.out.println("Inside image");
                newListing.getImages().add("./images/noimage.png");
                System.out.println("Helllo");
            }
            System.out.println("Customer Id: " + newListing.getCustomerEntity().getCustomerId());
            CustomerEntity c = customerSessionBeanLocal.retrieveCustomerByCustomerId(newListing.getCustomerEntity().getCustomerId());
            c.getListingList().add(newListing);
            System.out.println("Created");
            newListing.setCustomerEntity(c);
            em.persist(newListing);
            em.merge(c);
            em.flush();
            em.refresh(newListing);
            return newListing;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CreateListingException("Listing with same name already exists");
            } else {
                throw new CreateListingException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new CreateListingException("An unexpected error has occurred: " + ex.getMessage());
        }
    }

    @Override
    public ListingEntity updateListing(ListingEntity listingEntity) {
        ListingEntity listingToUpdate = null;
        try {
            if (listingEntity.getListingId() != null) {
                listingToUpdate = retrieveListingById(listingEntity.getListingId());
                listingToUpdate.setListingTitle(listingEntity.getListingTitle());
                listingToUpdate.setListingDescription(listingEntity.getListingDescription());
                listingToUpdate.setCostPerDay(listingEntity.getCostPerDay());
                listingToUpdate.setCategory(listingEntity.getCategory());
                listingToUpdate.setImages(listingEntity.getImages());
                listingToUpdate.setListingAvailable(listingEntity.getListingAvailable());
            }
        } catch (InvalidListingException ex) {
            System.out.println(ex.getMessage());
        }
        return listingToUpdate;
    }

    @Override
    public Boolean isLister(ListingEntity listing, Long listingId) {
        try {
            ListingEntity retrievedResult = retrieveListingById(listingId);
            System.out.println(retrievedResult.getCustomerEntity().getCustomerId());
            System.out.println("*****************************************************");
            if (listing.getListingId() != null) {
                ListingEntity result = retrieveListingById(listing.getListingId());
                System.out.println(result.getCustomerEntity().getCustomerId());
                if (result.getCustomerEntity().getCustomerId() == retrievedResult.getCustomerEntity().getCustomerId()) {
                    return true;
                }
            }

        } catch (InvalidListingException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public void deleteListing(Long listingId) throws InvalidListingException {
        ListingEntity listingEntity = em.find(ListingEntity.class, listingId);
        try {
            em.remove(listingEntity);
        } catch (NoResultException ex) {
            throw new InvalidListingException("Invalid listingEntity ID. Bid does not exists.");
        }
    }

    @Override
    public List<ListingEntity> retrieveListingList() {
        Query query = em.createQuery("SELECT s FROM ListingEntity s");
        List<ListingEntity> results = query.getResultList();
        for (ListingEntity l : results) {
            l.getPaymentEntities().size();
            l.getRequestList().size();
        }
        return query.getResultList();
    }
    
    @Override
    public List<ListingEntity> retrieveListingByCustomerId(Long id) throws CustomerNotFoundException{
        try {
            System.out.println("MADE IT TO SESSION BEAN");
            CustomerEntity ce = customerSessionBeanLocal.retrieveCustomerByCustomerId(id);
            Query query = em.createQuery("SELECT s FROM ListingEntity s WHERE s.customerEntity = :inCustomerId");
            query.setParameter("inCustomerId", ce);
            List<ListingEntity> results = query.getResultList();
            for(int i = 0; i < results.size(); i++){
                results.get(i).getImages().size();
                results.get(i).getPaymentEntities().size();
                results.get(i).getRequestList().size();
            }
            return results;
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer not found exception thrown");
        }
    }

    @Override
    public ListingEntity retrieveListingById(Long listingId) throws InvalidListingException {
        ListingEntity listingEntity = em.find(ListingEntity.class, listingId);
        if (listingEntity != null) {
            return listingEntity;
        } else {
            throw new InvalidListingException("Invalid listingEntity ID");
        }
    }
}
