/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "retrieveByCustomerRsp", propOrder = {
    "listings"
})
public class RetrieveByCustomerRsp {
    private List<ListingEntity> listings;
    
    public RetrieveByCustomerRsp(){
        
    }
    
    public RetrieveByCustomerRsp(List<ListingEntity> listings){
        this.listings = listings;
    }
    
    public List<ListingEntity> getListings(){
        return this.listings;
    }
    
    public void setListings(List<ListingEntity> listings){
        this.listings = listings;
    }
}
