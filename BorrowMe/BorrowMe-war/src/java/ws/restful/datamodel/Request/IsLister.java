/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "isLister", propOrder = {
    "status"
})
public class IsLister {
    private Boolean status;
    
    public IsLister(){
        
    }
    
    public IsLister(Boolean status){
        this.status = status;
    }
    
    public Boolean getStatus(){
        return this.status;
    }
    
    public void setStatus(Boolean status){
        this.status = status;
    }
    
}
