package ws.restful.datamodel;

import entity.ItemEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createItemReq", propOrder = {
    "item"
})

public class CreateItemReq
{    
    private ItemEntity item;

    
    
    public CreateItemReq()
    {
    }

    
    
    public CreateItemReq(ItemEntity item)
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
