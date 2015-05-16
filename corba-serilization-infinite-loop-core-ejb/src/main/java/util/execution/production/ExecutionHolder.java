package util.execution.production;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.execution.Execution;


/**
 * This object instantiates the executionors .
 */
public class ExecutionHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionHolder.class);

    /**
     * Each instance that will satisfy this injection will require its own producer returning execution instances with
     * this qualifier. Now the point is that there is a difference in behavior between WELD and Open CDI here.
     *
     */
    @Inject
    @ExecutionQualifier
    Instance<Execution<?, ?>> executionInstances;

    Map<Class<?>, Execution> exeuctionors = new HashMap<Class<?>, Execution>();


    @SuppressWarnings("unchecked")
    public <T> T getExecutionFor(Class<T> executionClassMapKey) {
        return (T) exeuctionors.get(executionClassMapKey);
    }

    /**
     * asks each executioner
     */
    public void initAllExecutionorsForInjectionPointQualifier(String annotationQualifierValue) {
        // initialize all Executions with whatever qualifier existed in the annoation of the injection point
        for (Execution currentExecution : executionInstances) {
            currentExecution.initialize(annotationQualifierValue);
            exeuctionors.put(currentExecution.getExecutionerTypeKey(), currentExecution);
        }        
    }
}
