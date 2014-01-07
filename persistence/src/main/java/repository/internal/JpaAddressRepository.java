package repository.internal;

import model.adress.Address;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.AddressRepository;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Repository zum handlen von DB-Requests, die irgendwas mit Addresses zu tun haben.
 */
@Repository
public class JpaAddressRepository  implements AddressRepository{

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;


    @Transactional
    public Address findById(Long id) {
        if (id == null) {
            return null;
        }
        return getEntityManager().find(Address.class, id);
    }

    public List<Address> getAll() {
        Query query = getEntityManager().createQuery("SELECT a FROM Address a", Address.class);
        return query.getResultList();

    }

    @Transactional
    public void remove(Address entity) {
        Address toBeRemoved = getEntityManager().find(Address.class, entity.getId());
        getEntityManager().remove(toBeRemoved);
    }

    @Transactional
    public Address store(Address entity) {

        if (entity == null) {
            return null;
        }
        if (entity.getId() == null) {
            getEntityManager().persist(entity);

        } else {
            getEntityManager().merge(entity);
        }
        getEntityManager().flush();
        return findById(entity.getId());
    }
}
