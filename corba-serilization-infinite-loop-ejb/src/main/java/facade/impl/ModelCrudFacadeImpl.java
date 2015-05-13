/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.impl;

import facade.api.ModelCrudFacadeRemote;
import db.crud.EntityOperations;
import db.model.SomeEntity;
import inteceptor.FacadeInterceptor;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 *
 * @author b7godin
 */
@Stateless
@Interceptors(FacadeInterceptor.class)
@Remote(ModelCrudFacadeRemote.class)
public class ModelCrudFacadeImpl implements ModelCrudFacadeRemote {

    @Inject
    EntityOperations entityOperations;

    @Override
    public Long countEntities() {
        // also create an entity
        entityOperations.create();
        // it is irrelevant that we we are counting entities
        return entityOperations.countAll();
    }

    @Override
    public void deleteAll() {
        entityOperations.deleteAll();
    }

    @Override
    public int createInfiniteRecursiveEntity(int treeDepth) {
        return entityOperations.createInfiniteRecursiveEntity(treeDepth);
    }

    @Override
    public SomeEntity fetchEntityById(Integer id) {
        return entityOperations.fetchEntityById(id);
    }

}
