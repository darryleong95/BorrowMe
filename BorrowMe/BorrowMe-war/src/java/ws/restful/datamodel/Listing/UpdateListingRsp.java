/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Listing;

import entity.ListingEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "updateListingRsp", propOrder = {
    "listing"
})
public class UpdateListingRsp {
    private ListingEntity listing;
    
    public UpdateListingRsp(){
        
    }
    
    public UpdateListingRsp(ListingEntity listing){
        this.listing = listing;
    }
    
    public ListingEntity getListing(){
        return this.listing;
    }
    
    public void setListing(ListingEntity listing){
        this.listing = listing;
    }
}
