package ws.restful.datamodel.Item;

import entity.ItemEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "updateItemReq", propOrder = {
    "item"
})

public class UpdateItemReq 
{
    private ItemEntity item;

    
    
    public UpdateItemReq() 
    {
    }

    
    
    public UpdateItemReq(ItemEntity item)
    {
        this.item = item;
    }
    
    
    
    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
