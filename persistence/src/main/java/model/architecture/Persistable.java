package model.architecture;

import java.io.Serializable;

/**
 * Methods, a persistable entity has to provide
 *
 * @author stibe
 * @since 1.0.0
 */
public interface Persistable extends Serializable {

    Long getId();
    void setId(Long id);
    Integer getVersion();
    void setVersion(Integer version);
}
