package ws.restful.datamodel.Customer;

import javax.xml.bind.annotation.XmlType;



@XmlType(name = "errorRsp", propOrder = {
    "message"
})

public class ErrorRsp
{   
    private String message;

    
    
    public ErrorRsp()
    {
    }

    
    
    public ErrorRsp(String message)
    {
        this.message = message;
    }

    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}