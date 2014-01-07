package repository;

import model.adress.Address;

import java.util.List;

/**
 * Interface für das handlen von DB-Requests, die etwas mit Addresses zu tun haben.
 */
public interface AddressRepository {
    List<Address> getAll();

    Address store(Address dto);


    Address findById(Long id);

    void remove(Address entity);
}
