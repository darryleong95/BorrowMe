package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "retrieveListingRsp", propOrder = {
    "listing"
})

public class RetrieveListingRsp {

    private ListingEntity listing;

    public RetrieveListingRsp() {
    }

    public RetrieveListingRsp(ListingEntity listing) {
        this.listing = listing;
    }

    public ListingEntity getListing() {
        return listing;
    }

    public void setListing(ListingEntity listing) {
        this.listing = listing;
    }
}
