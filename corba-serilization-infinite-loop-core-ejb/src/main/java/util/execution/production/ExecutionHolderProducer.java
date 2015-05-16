
package util.execution.production;

import util.execution.production.ExecutionQualifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;



/**
 * A CDI Producer
  * 
 */
@ApplicationScoped
public class ExecutionHolderProducer {

    // resources being managed by the special producer
    Map<String, ExecutionHolder> mapAnnotationQualifierValueToMapOfDummyExecutionors = new ConcurrentHashMap<String, ExecutionHolder>();

    @Inject
    BeanManager beanManager;

    /**
     * a dummy resolver of @Injection of an execution holder for a given @ExectutionQualifier.
     *
     */
    @Produces
    @ExecutionQualifier
    public ExecutionHolder create(InjectionPoint ip) {

        String executionQualifierValue = getExecutionQualifierValue(ip);

        if (!mapAnnotationQualifierValueToMapOfDummyExecutionors.containsKey(executionQualifierValue)) {
            // create a new bean instance of a holder
            Bean<ExecutionHolder> bean = (Bean<ExecutionHolder>) beanManager.getBeans(ExecutionHolder.class).iterator().
                    next();
            CreationalContext<ExecutionHolder> creationalContextForBean = beanManager.createCreationalContext(bean);
            ExecutionHolder beanInstance = bean.create(creationalContextForBean);

            // initilize the resources being managed by the holder
            beanInstance.initAllExecutionorsForInjectionPointQualifier(executionQualifierValue);

            // and on it goes to the map
            mapAnnotationQualifierValueToMapOfDummyExecutionors.put(executionQualifierValue, beanInstance);
        }

        // give the full set of executionors for the  desired qualifier
        return mapAnnotationQualifierValueToMapOfDummyExecutionors.get(executionQualifierValue);
    }

    /**
     * Get the of Value of ExecutionQualifier annotaiton defined on the injection Point
     */
    private String getExecutionQualifierValue(InjectionPoint ip) {
        ExecutionQualifier channel = ip.getAnnotated().getAnnotation(ExecutionQualifier.class);
        if (channel == null || channel.value().isEmpty()) {         
            throw new RuntimeException(
                    "Sorry - this producer can only work for you if you use the ExecutionQualifier and give it a value.");
        }
        return channel.value();
    }

}
