/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Request;

import entity.RequestEntity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "createRequestRsp", propOrder = {
    "request"
})
public class CreateRequestRsp {
    private RequestEntity request;
    
    public CreateRequestRsp(){
        
    }
    
    public CreateRequestRsp(RequestEntity request){
        this.request = request;
    }
    
    public void setRequest(RequestEntity request){
        this.request = request;
    }
    
    public RequestEntity getRequest(){
        return this.request;
    }
}
