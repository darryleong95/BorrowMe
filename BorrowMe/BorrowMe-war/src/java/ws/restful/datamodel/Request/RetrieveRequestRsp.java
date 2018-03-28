package ws.restful.datamodel.Request;

import entity.RequestEntity;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "retrieveRequestRsp", propOrder = {
    "request"
})

public class RetrieveRequestRsp
{    
    private RequestEntity request;

    
    
    public RetrieveRequestRsp()
    {
    }

    
    
    public RetrieveRequestRsp(RequestEntity request)
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
