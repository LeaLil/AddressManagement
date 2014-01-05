package model.adress;

import model.architecture.AddressBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: tysjn
 * Date: 05.12.13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "address")
public class Address extends AddressBaseEntity {

    @Column(name = "firstname")
    private String prename;

    @Column(name = "lastname")
    private String name;

    @Column(name = "email")
    private String mailAddress;

    @Column(name = "homepage")
    private String web;

    @Column(name = "email_additional")
    private String mail2;

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMail2() {
        return mail2;
    }

    public void setMail2(String mail2) {
        this.mail2 = mail2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (mail2 != null ? !mail2.equals(address.mail2) : address.mail2 != null) return false;
        if (mailAddress != null ? !mailAddress.equals(address.mailAddress) : address.mailAddress != null) return false;
        if (name != null ? !name.equals(address.name) : address.name != null) return false;
        if (prename != null ? !prename.equals(address.prename) : address.prename != null) return false;
        if (web != null ? !web.equals(address.web) : address.web != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prename != null ? prename.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mailAddress != null ? mailAddress.hashCode() : 0);
        result = 31 * result + (web != null ? web.hashCode() : 0);
        result = 31 * result + (mail2 != null ? mail2.hashCode() : 0);
        return result;
    }

    @Override
    public Integer getVersion() {
        return 1;
    }

    @Override
    public void setVersion(Integer version) {
        //Do nothing... Just for... Interfaces!!!
    }
}
