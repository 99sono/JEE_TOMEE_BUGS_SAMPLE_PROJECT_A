/*
 * -----------------------------------------------------------------------------
 * Application     : WM 6
 * Revision        : $Revision: 335180 $
 * Revision date   : $Date: 2014-10-27 09:24:15 +0100 (Mon, 27 Oct 2014) $
 * Last changed by : $Author: b2kuaya $
 * URL             : $URL: http://almscdc.swisslog.com/repo/SWPD/Development/WM6/trunk/Software/WM6/wm6-components/wm6-commons-util/src/main/java/com/swisslog/wm6/commons/util/interceptors/FacadeInterceptor.java $
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
package inteceptor;

import java.io.Serializable;



/**
 * An implementation of the abstract interceptor that is causing problems in weblogic.
 *
 */
public class MdbInterceptor extends GenericLoggingInterceptor implements Serializable {

    /** The serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @Override
    protected String getLogContext() {
        return "MdB";
    }

}
