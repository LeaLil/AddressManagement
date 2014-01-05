package model.architecture;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: gid
 * Date: 14.10.13
 * Time: 09:32
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AddressBaseEntity implements Persistable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 }
