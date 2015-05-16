/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.execution;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import util.execution.production.ExecutionQualifier;

/**
 *
 * @author b7godin
 */
public class DummyStringToStringExecutionProducer {

    @Inject
    DummyStringToStringExecution executionImplThatNeedsToBeProduced;

    /**
     * Returns the selected implementation for AV Channel using the standard strategy pattern.
     */
    @Produces
    @ExecutionQualifier
    public DummyStringToStringExecution getExecutionForQualifierOnInjectionPoint(InjectionPoint ip) {
        // stt we want to get a hold of whatever value was placed in the qualifier
        ExecutionQualifier qualifierOnInjectionPoint = ip.getAnnotated().getAnnotation(ExecutionQualifier.class);

        // with the qualifier value we run some dummy initialization logic
        executionImplThatNeedsToBeProduced.initialize(qualifierOnInjectionPoint.value());

        // we finally can return an initiaized execution dummy object
        return executionImplThatNeedsToBeProduced;
    }
}
