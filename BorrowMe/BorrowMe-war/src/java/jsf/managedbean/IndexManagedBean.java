package jsf.managedbean;

import entity.CustomerEntity;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named(value = "indexManagedBean")
@RequestScoped
public class IndexManagedBean {

    private CustomerEntity selectedProfileToView;

    public void redirectProfile(ActionEvent event) {

        long profileIdToView = (long) event.getComponent().getAttributes().get("profileIdToView");
        System.out.println("redirecting to profile " + profileIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("profileIdToView", profileIdToView);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ViewProfile.xhtml");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void redirectOwnProfile(ActionEvent event) {
        setSelectedProfileToView((CustomerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));
        System.out.println("redirecting to own profile ");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("profileIdToView", selectedProfileToView.getCustomerId());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ViewProfile.xhtml");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public CustomerEntity getSelectedProfileToView() {
        return selectedProfileToView;
    }

    public void setSelectedProfileToView(CustomerEntity selectedProfileToView) {
        this.selectedProfileToView = selectedProfileToView;
    }

}
