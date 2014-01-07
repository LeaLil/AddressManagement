package model.architecture;

import java.io.Serializable;

/**
 * Methods, a persistable entity has to provide
 *
 */
public interface Persistable extends Serializable {

    Long getId();
    void setId(Long id);
}
