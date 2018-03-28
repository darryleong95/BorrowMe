package ws.restful.datamodel.Request;

import ws.restful.datamodel.Request.*;
import entity.RequestEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "createRequestReq", propOrder = {
    "request"
})

public class CreateRequestReq
{    
    private RequestEntity request;

    
    
    public CreateRequestReq()
    {
    }

    
    
    public CreateRequestReq(RequestEntity request)
    {
        this.request = request;
    }

    
    
    public RequestEntity getRequest() {
        return request;
    }

    public void setRequest(RequestEntity request) {
        this.request = request;
    }
}
