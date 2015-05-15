/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.api;

import db.model.SomeEntity;
import javax.ejb.Remote;

/**
 *
 * @author b7godin
 */
@Remote
public interface ModelCrudFacadeRemote {

    /**
     * @return total numer of eneitites in the db
     */
    Long countEntities();

    /**
     * Delete all entites
     */
    void deleteAll();

    /**
     * create an entity to refers to iself as parent and child
     */
    int createInfiniteRecursiveEntity(int treeDepth);

    /**
     * fetch an entity by id
     */
    SomeEntity fetchEntityById(Integer id);
}
