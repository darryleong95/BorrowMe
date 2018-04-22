/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "deleteRequestRsp", propOrder = {
    "status"
})
public class DeleteRequestRsp {
    private Boolean status;
    
    public DeleteRequestRsp(){
        
    }
    
    public DeleteRequestRsp(Boolean status){
        this.status = status;
    }
    
    public Boolean getStatus(){
        return this.status;
    }
    
    public void setStatus(Boolean status){
        this.status = status;
    }
}
