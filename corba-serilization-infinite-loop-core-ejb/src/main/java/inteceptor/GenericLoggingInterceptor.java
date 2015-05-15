/*
 * -----------------------------------------------------------------------------
 * Application     : WM 6
 * Revision        : $Revision: 335180 $
 * Revision date   : $Date: 2014-10-27 09:24:15 +0100 (Mon, 27 Oct 2014) $
 * Last changed by : $Author: b2kuaya $
 * URL             : $URL: http://almscdc.swisslog.com/repo/SWPD/Development/WM6/trunk/Software/WM6/wm6-components/wm6-commons-util/src/main/java/com/swisslog/wm6/commons/util/interceptors/GenericLoggingInterceptor.java $
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

import db.cdi.OrclDatabase;
import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * <p>
 * This interceptor base class is used around facades. Around each method invocation log out the method call, its
 * arguments, duration, return value or exception message.
 * </p>
 * <ul>
 * <li>It logs the parameters, return value, exception and execution time.</li>
 * </ul>
 *
 * <P>
 * The exception translation feature is particularly useful when the ejb being intercepted is prone to blow up due to,
 * for example, javax.validation.ConstraintViolationException which normally do not inform what particular constraint is
 * violated. The translation mechanism is able to discover the specific JPA validation that failed.
 *
 *
 */
public abstract class GenericLoggingInterceptor implements Serializable {
    /** The serialVersionUID */
    private static final long serialVersionUID = 1L;

    private static long countOfInterceptions = 0;
    private static long countOfNullEntityManager = 0;

    @Inject
    @OrclDatabase
    private EntityManager em;

    /**
     * The log context. Usually the log appender (handler) creates a log file per context. (e.g Facade, MdB)
     * <P>
     * For example, in the glassfish log folder a file named "wm6-MdB.log" will be created when context is MdB.
     */
    protected abstract String getLogContext();

    /**
     * The intercepting method. It assigns a log context, adds log messages around the call and translates exceptions.
     *
     * @param invocation
     *            The invocation context within the method is called
     * @throws Exception
     *             InvocationContext.proceed() throws Exception
     */
    @AroundInvoke
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Object aroundInvoke(InvocationContext invocation) throws Exception {
        incrementCountOfInterpcetions();
        Logger logger = LoggerFactory.getLogger(invocation.getTarget().getClass());

        // get more information for the trace message
        String clazzName = invocation.getTarget().getClass().getSimpleName();
        String methodName = invocation.getMethod().getName();
        Object[] parameters = invocation.getParameters();

        boolean tracked = isTraceEnabled(logger) || isTrackedMethod(invocation);

        Exception exception = null;
        Object returnValue = null;
        String entryPoint = clazzName + "." + methodName;
        MDC.put("LogContext", entryPoint);
        try {
            long startTime = System.currentTimeMillis();
            writeBeginTrace(logger, entryPoint, parameters, tracked);
            boolean logStack = false;
            try {
                if (em == null) {
                    incrementCountOfNullEntityManager();
                    logger.error(String.format(
                            "%n %1$s --------- DETECTED ENTITY MANAGER NULL IN INTERCEPTOR ---------------------",
                            entryPoint));
                }
                returnValue = invocation.proceed();
                // here we would get a null pointer exception
                em.flush();
            }  catch (Exception e) {
                logStack = true;
                exception = e;
            }

            writeEndTrace(logger, invocation, entryPoint, returnValue, exception, startTime, tracked, logStack);
        } finally {
            logger.info(String.format("COUNNT_OF_INTERCEPTIONS: %1$s ; COUNT_OF_NULL_ENTITY_MANAGERS: %2$s",
                    getCountOfInterceptions(), getCountOfNullEntityManager()));
            MDC.remove("LogContext");
        }

        if (exception != null) {
            throw new RuntimeException("Something blew up in method being intercepted. ", exception);
        } else {
            return returnValue;
        }
    }

    private void writeEndTrace(Logger logger, InvocationContext invocation, String entryPoint, Object returnValue,
            Exception exception, long startTime, boolean tracked, boolean logStack) {
        if (exception != null) {
            logger.error("Exception that in real life would have been transalated was: " + exception.getMessage(),
                    exception);
        }
        logger.info(String.format("%n<------- : FINISHED %1$s", entryPoint));        
    }

    private void writeBeginTrace(Logger logger, String entryPoint, Object[] parameters, boolean tracked) {
        logger.info(String.format("%nSTARTED %1$s : ------->  ", entryPoint));        
    }

    /**
     * Returns true if LogLevel of logger is debug or above.
     */
    private boolean isTraceEnabled(Logger logger) {
        return logger.isTraceEnabled();
    }

    /**
     * Returns true if this method call should be logged or not.
     */
    private boolean isTrackedMethod(InvocationContext invocation) {        
        return true;
    }

    private static synchronized void incrementCountOfInterpcetions() {
        countOfInterceptions++;
    }

    private static synchronized void incrementCountOfNullEntityManager() {
        countOfNullEntityManager++;
    }

    private static synchronized long getCountOfInterceptions() {
        return countOfInterceptions;
    }

    private static synchronized long getCountOfNullEntityManager() {
        return countOfNullEntityManager;
    }
}
