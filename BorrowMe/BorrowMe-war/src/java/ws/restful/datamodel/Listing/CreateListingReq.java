package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createListingReq", propOrder = {
    "listing"
})

public class CreateListingReq
{    
    private ListingEntity listing;

    
    
    public CreateListingReq()
    {
    }

    
    
    public CreateListingReq(ListingEntity listing)
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
