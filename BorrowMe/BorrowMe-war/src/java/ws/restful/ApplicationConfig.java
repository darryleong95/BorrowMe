package ws.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

//Controls all Resource class available for this web application

@javax.ws.rs.ApplicationPath("Resources") //Note: Resources 

public class ApplicationConfig extends Application
{
    @Override
    public Set<Class<?>> getClasses() 
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources)
    {
        resources.add(ws.restful.CustomerResource.class);
        resources.add(ws.restful.DebugExceptionMapper.class);
        resources.add(ws.restful.FeedbackResource.class);
        resources.add(ws.restful.ListingResource.class);
        resources.add(ws.restful.PaymentResource.class);
        resources.add(ws.restful.RequestResource.class);
    }   
  
}