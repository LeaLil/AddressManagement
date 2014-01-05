package repository.internal;

import model.adress.Address;
import org.springframework.stereotype.Repository;
import repository.AddressRepository;
import repository.architecture.JpaBasePersistableRepository;

/**
 * Created with IntelliJ IDEA.
 * User: tysjn
 * Date: 05.12.13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JpaAddressRepository extends JpaBasePersistableRepository<Address> implements AddressRepository{
}
