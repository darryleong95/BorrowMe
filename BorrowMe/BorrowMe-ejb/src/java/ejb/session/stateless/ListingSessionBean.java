/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ListingEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateListingException;
import util.exception.InvalidListingException;

/**
 *
 * @author User
 */
@Stateless
@Local(ListingSessionBeanLocal.class)
public class ListingSessionBean implements ListingSessionBeanLocal {

    @PersistenceContext(unitName = "BorrowMe-ejbPU")
    private EntityManager em;

    @Override
    public ListingEntity createListing(ListingEntity newListing) throws CreateListingException {
        //INCOMPLETE; STILL NEED TO GET MEMBER INFORMATION SOMEHOW AND ADD LISTING TO MEMBER AND VICE VERSA
        
        try {
            em.persist(newListing);
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
        em.merge(listingEntity);
        return listingEntity;
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
        return query.getResultList();
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
