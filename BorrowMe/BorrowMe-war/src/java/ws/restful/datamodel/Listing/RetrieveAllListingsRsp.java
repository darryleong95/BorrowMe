package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "retrieveAllListingsRsp", propOrder = {
    "listings"
})

public class RetrieveAllListingsRsp
{   
    private List<ListingEntity> listings;

    
    
    public RetrieveAllListingsRsp()
    {
    }

    
    
    public RetrieveAllListingsRsp(List<ListingEntity> listings)
    {
        this.listings = listings;
    }

    
    
    public List<ListingEntity> getListings() {
        return listings;
    }

    public void setListings(List<ListingEntity> listings) {
        this.listings = listings;
    }
}