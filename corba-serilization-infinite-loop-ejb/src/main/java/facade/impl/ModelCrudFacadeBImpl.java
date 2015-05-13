/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.impl;

import facade.api.ModelCrudFacadeRemoteB;
import db.crud.EntityOperations;
import inteceptor.FacadeInterceptor;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 *
 * @author b7godin
 */
@Stateless
@Interceptors(FacadeInterceptor.class)
public class ModelCrudFacadeBImpl implements ModelCrudFacadeRemoteB {

    @Inject
    EntityOperations entityOperations;

    @Override
    public Long countEntities() {
        // also create an entity
        entityOperations.create();
        // it is irrelevant that we we are counting entities
        return entityOperations.countAll();
    }


}
