package ws.restful.datamodel.Request;

import ws.restful.datamodel.Request.*;
import entity.RequestEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlType(name = "retrieveAllRequestsRsp", propOrder = {
    "requests"
})

public class RetrieveAllRequestsRsp
{   
    private List<RequestEntity> requests;

    
    
    public RetrieveAllRequestsRsp()
    {
    }

    
    
    public RetrieveAllRequestsRsp(List<RequestEntity> requests)
    {
        this.requests = requests;
    }

    
    
    public List<RequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }
}