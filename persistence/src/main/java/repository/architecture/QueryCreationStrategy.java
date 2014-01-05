package repository.architecture;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Map;

/**
 * For each search (except "getAll") a corresponding QueryCreationStrategy has to be defined
 *
 * @author stibe
 * @since 1.0.0
 */
public interface QueryCreationStrategy {

    <T> TypedQuery<T> createQuery(EntityManager em, T data, Map<String, Object> advancedParams);

}
