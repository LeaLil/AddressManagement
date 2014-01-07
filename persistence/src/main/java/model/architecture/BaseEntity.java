package model.architecture;

import javax.persistence.*;

/**
 * BaseEntity.
 * Von dieser Klasse erben alle anderen Entity-Klassen
 * Stellt die Verwendung einer ID sicher.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Persistable {

    /**
     * GenerationType.Identity sorgt für die Verwendung des Auto-Increment Mechanismuses.
     */
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
