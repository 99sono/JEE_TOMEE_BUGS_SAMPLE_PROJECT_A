package db.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SomeEntity Remember to run the sequenceTable.sql to create the database table
 * otherwise deployment errors will happen.
 */
@Entity
@Table(name = "SOME_ENTITY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CLASS_TYPE", discriminatorType = DiscriminatorType.STRING, length = 32)
@DiscriminatorValue("SOME_ENTITY")
public class SomeEntity extends AbstractEntityBase implements Serializable {

    private static final long serialVersionUID = 20L;

    @Id
    @TableGenerator(name = "idseq", allocationSize = 1, table = "testcaseSeq", pkColumnName = "seq_name", valueColumnName = "seq_count")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idseq")
    @Column(name = "ID")
    private int id;

    
    @Lob
    @Column(name = "TEXTLOB")
    private String text;

    /**
     * My parents
     */
    @ManyToOne()
    @JoinColumn(name = "PARENT_FK", referencedColumnName = "id")
    SomeEntity parent;

    /**
     * My children
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    List<SomeEntity> children;

    // //////////////////////////////
    // BEGIN: BOILER PALTE
    // //////////////////////////////
    public SomeEntity() {
        super();
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SomeEntity getParent() {
        return parent;
    }

    public void setParent(SomeEntity parent) {
        this.parent = parent;
    }

    public List<SomeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<SomeEntity> children) {
        this.children = children;
    }


}
