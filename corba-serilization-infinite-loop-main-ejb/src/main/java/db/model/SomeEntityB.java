package db.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SomeEntity Remember to run the sequenceTable.sql to create the database table
 * otherwise deployment errors will happen.
 */
@Entity
@DiscriminatorValue("SOME_ENTITY_B")
public class SomeEntityB extends SomeEntity implements Serializable {

    private static final long serialVersionUID = 21L;

    
}
