package ws.restful.datamodel.Item;

import entity.ItemEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "retrieveAllItemsRsp", propOrder = {
    "items"
})

public class RetrieveAllItemsRsp
{   
    private List<ItemEntity> items;

    
    
    public RetrieveAllItemsRsp()
    {
    }

    
    
    public RetrieveAllItemsRsp(List<ItemEntity> items)
    {
        this.items = items;
    }

    
    
    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }
}