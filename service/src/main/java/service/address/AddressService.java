package service.address;

import model.adress.Address;

import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tysjn
 * Date: 05.12.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public interface AddressService {

    List<Address> getAll();

    void add(Address dto);

    void delete(Long id);

    InputStream createExportStream();

}
