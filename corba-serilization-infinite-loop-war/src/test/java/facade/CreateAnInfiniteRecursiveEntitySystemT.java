/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import facade.api.ProxyForModelCrudFacadeRemote;
import db.model.SomeEntity;
import org.junit.Before;
import org.junit.Test;
import util.jndi.JndiUtil;

/**
 *
 * @author b7godin
 */
public class CreateAnInfiniteRecursiveEntitySystemT {

    private static final String WAR_NAME = "corba-serilization-infinite-loop-war";

    private static int NUMBER_OF_FACADE_CALLS_TO_DO = 10;


    ProxyForModelCrudFacadeRemote remoteFacade;

    @Before
    public void before() {
        //java:global/generic-logging-interceptor-bug/ModelCrudFacadeImpl!facade.ModelCrudFacadeRemote
        //java:global/generic-logging-interceptor-bug/ModelCrudFacadeImpl!facade.ModelCrudFacadeRemote
        remoteFacade = JndiUtil.SINGLETON.resolveBean(WAR_NAME, "ProxyForModelCrudFacadeImpl",
                ProxyForModelCrudFacadeRemote.class);

        // 
        System.out.println("Deleting all entiteis before test");
        //remoteFacade.deleteAll();
    }

    @Test
    public void tryToGetInterceptorToDie() throws InterruptedException {
        Integer idEntityThatIsIfinitelyRecursive = remoteFacade.createInfiniteRecursiveEntity(1000);
        SomeEntity infiniteRecursiveEntity = remoteFacade.fetchEntityById(idEntityThatIsIfinitelyRecursive);
        System.out.println(infiniteRecursiveEntity);
    }
}
