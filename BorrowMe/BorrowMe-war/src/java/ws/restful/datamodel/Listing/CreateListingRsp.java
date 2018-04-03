package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "createListingRsp", propOrder = {
    "id"
})

public class CreateListingRsp
{    
    private ListingEntity listingEntity;

    
    
    public CreateListingRsp()
    {
    }

    
    
    public CreateListingRsp(ListingEntity listingEntity)
    {
        this.listingEntity = listingEntity;
    }

    
    
    public ListingEntity getListingEntity() {
        return listingEntity;
    }

    public void setListingEntity(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }
}