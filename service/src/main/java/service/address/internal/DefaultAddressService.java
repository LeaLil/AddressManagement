package service.address.internal;

import model.adress.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AddressRepository;
import service.address.AddressService;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;

/*
 */
@Service
@Named("defaultAddressService")
public class DefaultAddressService implements AddressService, Serializable {

    @Inject
    private AddressRepository repo;


    @Override
    public List<Address> getAll(){
        List<Address> list = repo.getAll();
        return list; 
    }

    @Override
    public void add(Address dto) {
        repo.store(dto);
    }

    @Override
    public void delete(Long id) {
        Address entity = repo.findById(id);
        if(entity != null){
            repo.remove(entity);
        }
    }
}
