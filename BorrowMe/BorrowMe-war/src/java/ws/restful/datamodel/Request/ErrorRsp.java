/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel.Request;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "errorRsp", propOrder = {
    "message"
})
public class ErrorRsp {

    private String message;

    public ErrorRsp() {
    }

    public ErrorRsp(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}