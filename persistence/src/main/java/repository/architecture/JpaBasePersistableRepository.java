package repository.architecture;

import model.architecture.Persistable;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A JPA based implementation of the BaseRepository interface, implementing all
 * methods with the underlying EntityManager. This class doesn't make any
 * assumptions about the type of the entity and even the type for the key
 * itself. It is up to the implementation to specify the entity and the key as
 * the type variables of this class
 *
 * @param <T>       the type handled by the repository service. This type must be
 *                  known to the underlying persistence technology.
 * @author stibe
 * @since 1.0.0
 */
public abstract class JpaBasePersistableRepository<T extends Persistable> implements PersistableRepository<T> {

    /**
     * The transaction scoped entity manager, handled outside this class by
     * frameworks (Spring) or containers (EJB)
     */
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    /**
     * The entity class that will be handled by this repository
     */
    private final Class<T> entityClass = resolveEntityClass();

    /**
     * Loads an entity through the {@link javax.persistence.EntityManager}s find
     * method using the primary key as the id and the entity class as the
     * expected type.
     *
     * @param id - The primary key for the object to be found
     * @return the object or null if not available with the particular primary
     *         key.
     */
    public T findById(Long id) {
        if (id == null) {
            return null;
        }
        return getCurrentEntityManager().find(this.entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        String className = resolveEntityClass().getSimpleName();
        Query query = getCurrentEntityManager().createQuery("SELECT " + className.toLowerCase() + " FROM " + className + " " + className.toLowerCase(), resolveEntityClass());
        return query.getResultList();
    }

    /**
     * Removes the object by calling the remove method on the
     * {@link javax.persistence.EntityManager}
     *
     * @param entity - that should be deleted
     */
    @Transactional
    public void remove(T entity) {
        entity = (T) getCurrentEntityManager().find(resolveEntityClass(), entity.getId());
        getCurrentEntityManager().merge(entity);
        getCurrentEntityManager().remove(entity);
    }

    /**
     * Stores the object by calling the persist method on the
     * {@link javax.persistence.EntityManager}.
     *
     * @param entity the entity to be stored into the repository
     */
    @Transactional
    public T store(T entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getId() == null) {
            getCurrentEntityManager().persist(entity);

        } else {
            getCurrentEntityManager().merge(entity);
        }
        getCurrentEntityManager().flush();
        return findById(entity.getId());
    }

    /**
     * @return the currently active entity manager
     */
    protected EntityManager getCurrentEntityManager() {
        return entityManager;
    }

    /**
     * Helper method to retrieve the single result of the query in a type-safe
     * way. If the result does not contain any item, <code>null</code> is
     * returned.
     *
     * @param query - the query to retrieve the single result from
     * @return the single result of the query, or null, if the query does not
     *         contain any item in the result
     * @throws javax.persistence.NonUniqueResultException if the query contains more than one item in the result
     */
    protected T getSingleResult(TypedQuery<T> query) {
        T result;

        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }

        return result;
    }

    /**
     * Internal, helper method returning a pattern for the given search term
     * which might include <code>'*'</code>, replaced by <code>'%'</code>
     * characters.
     *
     * @param searchParam the search term to turn into a valid search pattern
     * @return the SQL search pattern or <code>'%'</code>, if the given
     *         parameter is <code>null</code>
     */
    protected String createSearchPattern(String searchParam) {
        if (searchParam != null) {
            return "%" + searchParam.replace('*', '%') + "%";
        } else {
            return "%";
        }
    }

    /**
     * Creates a typed query using this repository's entity class.
     *
     * @param query - mandatory
     * @return Never null.
     * @since 2.0
     */
    protected TypedQuery<T> createTypedQuery(String query) {
        return getCurrentEntityManager().createQuery(query, entityClass);
    }

    public List<T> executeDynamicQuery(T data) {
        if (data == null) {
            throw new IllegalStateException("Param 'data' must not be null");
        }
        return executeDynamicQuery(data, null);
    }

    public List<T> executeDynamicQuery(T data, Map<String, Object> advancedParams) {
        if (data == null) {
            throw new IllegalStateException("Param 'data' must not be null");
        }
        Map<String, Object> strategies = DefaultContextProvider.getAnnotatedBean(Strategy.class);
        String beanName = null;
        for (String key : strategies.keySet()) {
            Object strategy = strategies.get(key);

            if (strategy.getClass().getAnnotation(Strategy.class).entityClass() == resolveEntityClass() && strategy.getClass().getAnnotation(Strategy.class).isDefaultStrategy()) {
                String className = strategy.getClass().getSimpleName();
                beanName = className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
            }
        }
        if (StringUtils.isEmpty(beanName)) {
            return new ArrayList<T>();
        }
        return executeDynamicQuery(beanName, data, advancedParams);
    }


    public List<T> executeDynamicQuery(String queryStrategyName, T data, Map<String, Object> advancedParams) {
        if (data == null) {
            throw new IllegalStateException("Param 'data' must not be null");
        }
        if (StringUtils.isEmpty(queryStrategyName)) {
            return executeDynamicQuery(data, advancedParams);
        }
        QueryCreationStrategy strategy = DefaultContextProvider.getBean(queryStrategyName);
        if (strategy != null) {
            TypedQuery<T> query = strategy.createQuery(getCurrentEntityManager(), data, advancedParams);
            return query.getResultList();
        }
        return new ArrayList<T>();
    }

    public T getSingleResultSavely(TypedQuery<T> query) {
        List<T> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    private Class<T> resolveEntityClass() {
        try {
            return ReflectionHelper.determineFirstParameterClass(getClass());
        } catch (ReflectionHelperException e) {
            throw new IllegalStateException("Could not resolve Entity class: " + e.getMessage());
        }
    }
}
