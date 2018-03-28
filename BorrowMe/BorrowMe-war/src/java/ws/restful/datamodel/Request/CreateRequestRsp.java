package ws.restful.datamodel.Request;

import entity.RequestEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "createBookRsp", propOrder = {
    "id"
})

public class CreateRequestRsp
{    
    private RequestEntity requestEntity;

    
    
    public CreateRequestRsp()
    {
    }

    
    
    public CreateRequestRsp(RequestEntity requestEntity)
    {
        this.requestEntity = requestEntity;
    }

    
    
    public RequestEntity getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }
}