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
@XmlType(name = "createRequestReq", propOrder = {
    "request",
    "startDateStr",
    "endDateStr"
})

public class CreateRequestReq {
    private RequestEntity request;
    private String startDateStr;
    private String endDateStr;
    
    public CreateRequestReq(){
        
    }
    
    public CreateRequestReq(RequestEntity request){
        this.request =  request;
    }
    
    public void setRequest(RequestEntity request){
        this.request = request;
    }
    
    public RequestEntity getRequest(){
        return this.request;
    }

    /**
     * @return the startDateStr
     */
    public String getStartDateStr() {
        return startDateStr;
    }

    /**
     * @param startDateStr the startDateStr to set
     */
    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    /**
     * @return the endDateStr
     */
    public String getEndDateStr() {
        return endDateStr;
    }

    /**
     * @param endDateStr the endDateStr to set
     */
    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
}
