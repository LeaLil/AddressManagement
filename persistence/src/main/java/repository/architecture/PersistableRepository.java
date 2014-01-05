package repository.architecture;



import model.architecture.Persistable;

import java.util.List;
import java.util.Map;

/**
 * Central Interface to provide a unified interface for all repositories. This
 * interface defines generic methods that can be used for any Entity to be
 * stored, removed and loaded from the underlying persistence technology. A
 * update method is not provided, this has to be defined in the explicit
 * interface created for a concrete entity or in the service class.
 * 
 * @param <PERSISTABLETYPE>
 *            the type handled by the repository service. This type must be
 *            known to the underlying persistence technology
 *
 * @author stibe
 * @since 1.0.0
 */
public interface PersistableRepository<PERSISTABLETYPE extends Persistable> {

	/**
	 * Finder method to load a object by it's primary key. The primary key Type
	 * is derived by the type parameter K. This method returns null if the
	 * object can't be found, so the client must be prepared to accept null
	 * return values.
	 * 
	 * @param id
	 *            the primary key for the object to be found
	 * 
	 * @return the object with the primary key or null if the object can't be
	 *         found
	 */
    PERSISTABLETYPE findById(Long id);
	
	/**
	 * 
	 * @return a list of all objects stored in the database
	 */
	List<PERSISTABLETYPE> getAll();

	/**
	 * Deletes the particular instance from the repository.
	 * 
	 * @param entity
	 *            that should be deleted
	 */
	void remove(PERSISTABLETYPE entity);

	/**
	 * Stores the particular instance into the repository.
	 * 
	 * @param entity
	 *            the entity to be stored into the repository.
	 */
    PERSISTABLETYPE store(PERSISTABLETYPE entity);

    List<PERSISTABLETYPE> executeDynamicQuery(String queryName, PERSISTABLETYPE data, Map<String, Object> advancedParams);

    List<PERSISTABLETYPE> executeDynamicQuery(PERSISTABLETYPE data, Map<String, Object> advancedParams);

    List<PERSISTABLETYPE> executeDynamicQuery(PERSISTABLETYPE data);

}
