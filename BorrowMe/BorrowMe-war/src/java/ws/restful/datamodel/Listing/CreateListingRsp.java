package ws.restful.datamodel.Item;

import entity.ItemEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "createBookRsp", propOrder = {
    "id"
})

public class CreateItemRsp
{    
    private ItemEntity itemEntity;

    
    
    public CreateItemRsp()
    {
    }

    
    
    public CreateItemRsp(ItemEntity itemEntity)
    {
        this.itemEntity = itemEntity;
    }

    
    
    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }
}