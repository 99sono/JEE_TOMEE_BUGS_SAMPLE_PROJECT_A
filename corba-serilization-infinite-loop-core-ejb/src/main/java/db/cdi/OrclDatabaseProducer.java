/*
 * -----------------------------------------------------------------------------
 * Application     : WM 6
 * Revision        : $Revision: 274678 $
 * Revision date   : $Date: 2013-08-08 13:39:49 +0200 (Thu, 08 Aug 2013) $
 * Last changed by : $Author: b7rotsg $
 * URL             : $URL: http://almscdc.swisslog.com/repo/SWPD/Development/WM6/trunk/Software/WM6/wm6-components/wm6-commons-util/src/main/java/com/swisslog/wm6/commons/util/cdi/Wm6DatabaseProducer.java $
 * 
 * -----------------------------------------------------------------------------
 * Copyright
 * This software module including the design and software principals used
 * is and remains the property of Swisslog and is submitted with the
 * understanding that it is not to be reproduced nor copied in whole or in
 * part, nor licensed or otherwise provided or communicated to any third
 * party without Swisslog's prior written consent.
 * It must not be used in any way detrimental to the interests of Swisslog.
 * Acceptance of this module will be construed as an agreement to the above.
 *
 * All rights of Swisslog remain reserved. Swisslog and WarehouseManager
 * are trademarks or registered trademarks of Swisslog. Other products
 * and company names mentioned herein may be trademarks or trade names of
 * their respective owners. Specifications are subject to change without
 * notice.
 * -----------------------------------------------------------------------------
 */
package db.cdi;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * CDI producer of entity manager for WM 6 persistence unit.
 * 
 */
public class OrclDatabaseProducer {

    @PersistenceContext(unitName = "ContainerManagedXaPU")
    private EntityManager wm6EntityManager;

    
    @Produces
    @OrclDatabase
    public EntityManager createWm6EntityManager() {
        return wm6EntityManager;
    }
}
