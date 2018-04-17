/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ListingEntity;
import java.util.List;
import util.exception.CreateListingException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidListingException;

public interface ListingSessionBeanLocal {
    public ListingEntity createListing(ListingEntity listingEntity) throws CreateListingException;

    public ListingEntity updateListing(ListingEntity listingEntity);

    public void deleteListing(Long listingId) throws InvalidListingException;

    public List<ListingEntity> retrieveListingList();

    public ListingEntity retrieveListingById(Long listingId) throws InvalidListingException;

    public Boolean isLister(ListingEntity listing, Long listingId);

    public List<ListingEntity> retrieveListingByCustomerId(Long id) throws CustomerNotFoundException;
}
