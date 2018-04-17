/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Request;

import entity.RequestEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "requestMadeRsp", propOrder = {
    "requests"
})
public class RequestMadeRsp {
    private List<RequestEntity> requests;
    
    public RequestMadeRsp(){
        
    }
    
    public RequestMadeRsp(List<RequestEntity> requests){
        this.requests = requests;
    }
    
    public List<RequestEntity> getRequests(){
        return this.requests;
    }
    
    public void setRequests(List<RequestEntity> requests){
        this.requests = requests;
    }
}
