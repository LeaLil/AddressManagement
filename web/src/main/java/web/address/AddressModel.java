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
 * Klasse enthält alle Informationen, die in irgend einer art auf der View angezeigt werden.
 * Stellt entsprechende Methoden zur Verwaltung bereit.
 */
@ManagedBean(name = "addressModel")
@SessionScoped
public class AddressModel implements Serializable {

    /**
     * Werte der aktuellen zu bearbeitenden, löschenden oder hinzugefügten Adresse
     */
    private Address created;

    /**
     * Wert, der in der Tabelle selektiert wird. Nicht zwingend equals(created)!
     */
    private Address selected;

    /**
     * Wird ein Wert hinzugefügt oder bearbeitet?
     */
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


    /**
     * Hinzugefügte Addresse in der View hinzufügen
     */
    public void saveCreated() {

        existingAddressList.add(getCreated());
        init();
    }

    /**
     * gelöschte Addresse aus der View entfernen
     */
    public void deleteSelected() {
        existingAddressList.remove(getSelected());
        init();

    }

    /**
     * Initialisierung aller nötigen Variablen
     */
    public void init() {
        selected = new Address();
        created = new Address();
        addMode = true;
    }

    /**
     * Methode zur überprüfung des hinzuzufügenden oder upzudatenden Wertes (Addresse)
     * @return
     */
    public boolean isCreatedValid() {
        if (created != null) {
            return created.isValid();
        }
        return false;
    }
}
