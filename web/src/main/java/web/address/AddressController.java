package web.address;

import model.adress.Address;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.address.AddressService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Controller zum handlen aller Actions auf der Address-View.
 */
@ManagedBean(name = "addressController")
@SessionScoped
public class AddressController implements Serializable{


    public void setService(AddressService service) {
        this.service = service;
    }

    @ManagedProperty(value="#{defaultAddressService}")
    private AddressService service;

    public void setModel(AddressModel model) {
        this.model = model;
    }

    @ManagedProperty(value="#{addressModel}")
    private AddressModel model;

    public AddressController() {
//        model = new AddressModel();
    }

    /**
     * Laden aller Adressen aus der DB
     */
    public void init() {
        model.setExistingAddressList(service.getAll());
    }


    /**
     * Alle Adressen aus der DB
     * @return
     */
    public java.util.List<Address> get(){
        return service.getAll();
    }

    /**
     * Hinzufügen der aktuellen Daten
     */
    public void addAction() {
        if (model.isCreatedValid()) {
            service.add(model.getCreated());
            model.saveCreated();

        }else {
            addErrorMsg("Die eingegebenen Werte sind nicht valid. Bitte überprüfen");
        }

    }

    /**
     * Aktualisieren des momentan ausgewählten Wertes
     */
    public void updateAction() {
        if(model.isCreatedValid()) {
            service.add(model.getCreated());
            model.init();
            addSuccessMsg("Wert wurde erfolgreich upgedated");
        }else {
            addErrorMsg("Die eingegebenen Werte sind nicht valid. Bitte überprüfen");
        }
     }

    /**
     * Löschen der aktuell selektierten Action
     */
    public void deleteAction() {
        service.delete(model.getCreated().getId());
        model.deleteSelected();
        addSuccessMsg("Wert wurde erfolgreich gelöscht");
//        service.delete(id);
//        return new ResponseMessage(ResponseMessage.Type.success, "address with id: " + id + " deleted", null);
    }

    /**
     * Abrechen des Hinzufügen/Editierungsvorgangs
     */
    public void cancelAction() {
        model.init();
    }

    /**
     * Auswahl eines Wertes aus der Tabelle
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        model.setCreated(model.getSelected());
        model.setAddMode(false);
    }

    public void onRowUnselect(UnselectEvent event) {
        model.init();
    }


    private void addSuccessMsg(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Erfolg", msg));

    }

    private void addErrorMsg(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", msg));
    }

}
