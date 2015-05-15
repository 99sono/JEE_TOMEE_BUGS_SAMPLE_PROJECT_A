/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.api;

import db.model.SomeEntity;
import javax.ejb.Remote;

/**
 * The facade that will implement this RemoEJB interface will call other internal facades.
 *
 * The idea is that perhaps when we are doing CORBA deserialization in a JDK environment the classes that are being used
 * to deserialize are not exactly the same ones as the CORBA within the container itself.
 * So We want to make a Remote
 * EJB call from within the conainer itself.
 *
 * @author b7godin
 */
@Remote
public interface ProxyForModelCrudFacadeRemote {

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
