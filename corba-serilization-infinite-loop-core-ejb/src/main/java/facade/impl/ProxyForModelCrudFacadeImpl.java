/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.impl;

import facade.api.ProxyForModelCrudFacadeRemote;
import facade.api.ModelCrudFacadeRemote;
import db.model.SomeEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * We do not want this EJB to start transactions - it will simply delegate the task to another EJB which will indeed
 * need to create a transaction.
 *
 * @author b7godin
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ProxyForModelCrudFacadeImpl implements ProxyForModelCrudFacadeRemote {

    @EJB
    ModelCrudFacadeRemote actualFacade;

    @Override
    public Long countEntities() {
        return actualFacade.countEntities();
    }

    @Override
    public void deleteAll() {
        actualFacade.deleteAll();
    }

    @Override
    public int createInfiniteRecursiveEntity(int treeDepth) {
        return actualFacade.createInfiniteRecursiveEntity(treeDepth);
    }

    @Override
    public SomeEntity fetchEntityById(Integer id) {
        SomeEntity entityToReturn = actualFacade.fetchEntityById(id);
        return entityToReturn;
    }

}
