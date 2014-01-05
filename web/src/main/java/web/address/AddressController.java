package web.address;

import model.adress.Address;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.address.AddressService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: tysjn
 * Date: 05.12.13
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
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


    public void init() {
        model.setExistingAddressList(service.getAll());
    }


    public java.util.List<Address> get(){
        return service.getAll();
    }

    public void addAction() {
        service.add(model.getCreated());
        model.saveCreated();
    }

    public void updateAction() {
        service.add(model.getCreated());
        model.init();
    }

    public void deleteAction() {
        service.delete(model.getCreated().getId());
        model.deleteSelected();
//        service.delete(id);
//        return new ResponseMessage(ResponseMessage.Type.success, "address with id: " + id + " deleted", null);
    }

    public void cancelAction() {
        model.init();
    }

    public void onRowSelect(SelectEvent event) {
        model.setCreated(model.getSelected());
        model.setAddMode(false);
    }

    public void onRowUnselect(UnselectEvent event) {
        model.init();
    }

}
