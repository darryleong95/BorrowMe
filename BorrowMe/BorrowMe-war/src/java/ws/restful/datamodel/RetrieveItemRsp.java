package ws.restful.datamodel;

import entity.ItemEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "retrieveItemRsp", propOrder = {
    "item"
})

public class RetrieveItemRsp
{    
    private ItemEntity item;

    
    
    public RetrieveItemRsp()
    {
    }

    
    
    public RetrieveItemRsp(ItemEntity item)
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
