/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author b7godin
 */
@MappedSuperclass
public abstract class AbstractEntityBase implements Serializable {

    /**
     * Number of transactions made to this entity. Used for optimistic locking.
     */
    @javax.persistence.Version
    @javax.persistence.Column(name = "TRANSACTION_COUNT")
    private Integer transactionCount;

}
