package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "createListingRsp", propOrder = {
    "listing"
})

public class CreateListingRsp {

    private ListingEntity listingEntity;

    public CreateListingRsp() {
    }

    public CreateListingRsp(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }

    public ListingEntity getListing() {
        return listingEntity;
    }

    public void setListing(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }
}
