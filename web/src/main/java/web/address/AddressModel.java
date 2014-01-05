package web.address;

import model.adress.Address;
import org.primefaces.model.SelectableDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.context.request.SessionScope;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lea on 04.01.14.
 */
@ManagedBean(name = "addressModel")
@SessionScoped
public class AddressModel implements Serializable {

    private Address created;
    private Address selected;
    private boolean addMode;

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    private List<Address> existingAddressList;

    public AddressModel() {
        init();
    }

    public Address getCreated() {
        return created;
    }

    public void setCreated(Address created) {
        this.created = created;
    }

    public Address getSelected() {
        return selected;
    }

    public void setSelected(Address selected) {
        this.selected = selected;
    }

    public List<Address> getExistingAddressList() {
//        existingAddressList = new ArrayList<Address>();
        return existingAddressList;
    }

    public void setExistingAddressList(List<Address> existingAddressList) {
        this.existingAddressList = existingAddressList;
    }

    public void saveCreated() {

        existingAddressList.add(getCreated());
        init();
    }

    public void deleteSelected() {
        existingAddressList.remove(getSelected());
        init();

    }
    public void init() {
        selected = new Address();
        created = new Address();
        addMode = true;
    }
}
