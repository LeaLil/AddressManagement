package model.adress;

import com.sun.istack.internal.NotNull;
import model.architecture.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Entity-Klasse
 * Mapping zu der DB-Tabelle 'address'
 * übernimmt zudem allfällige Validierungsaufgaben
 */
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Column(name = "firstname")
    @NotNull
    private String prename;

    @Column(name = "lastname")
    @NotNull
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

    public boolean isValid() {
        if (name == "") {

            return false;
        }
        if (prename == "") {
            return false;
        }
        if (!validateMail()) {
            return false;
        }
        return validateWeb();
    }

    /**
     * Regex by http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/, 7.1.2014
     *
     * @return
     */
    private boolean validateMail() {
        String mailRegex = "^[_A-Za-z0-9]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        if ((mailAddress != null && mailAddress != "")) {
            if (!mailAddress.matches(mailRegex)) {
                return false;
            }

        }
        if ((mail2 != null && mail2 != "")) {
            if (!mail2.matches(mailRegex)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateWeb() {
        if (web == "" || web == null) {
            return true;
        }else {
            try {
                URI url = new URI(web);
                return true;
            } catch (URISyntaxException e) {
                return false;
            }
        }
    }
}
