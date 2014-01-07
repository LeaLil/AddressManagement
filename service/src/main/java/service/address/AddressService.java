package service.address;

import model.adress.Address;

import java.io.InputStream;
import java.util.List;

/**
 * Stellt sicher, dass die implementierenden Klassen alle nötigen Methoden zum handlen von Addressen bereitstellen
 */
public interface AddressService {

    List<Address> getAll();

    void add(Address dto);

    void delete(Long id);

}
