package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "updateListingReq", propOrder = {
    "listing"
})

public class UpdateListingReq 
{
    private ListingEntity listing;

    
    
    public UpdateListingReq() 
    {
    }

    
    
    public UpdateListingReq(ListingEntity listing)
    {
        this.listing = listing;
    }
    
    
    
    public ListingEntity getListing() {
        return listing;
    }

    public void setListing(ListingEntity listing) {
        this.listing = listing;
    }
}
