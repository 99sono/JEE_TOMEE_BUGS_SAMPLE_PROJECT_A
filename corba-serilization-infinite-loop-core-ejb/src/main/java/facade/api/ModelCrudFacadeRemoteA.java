/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.api;

import javax.ejb.Remote;

/**
 *
 * @author b7godin
 */
@Remote
public interface ModelCrudFacadeRemoteA {

    /**
     * @return total numer of eneitites in the db
     */
    Long countEntities();
}
