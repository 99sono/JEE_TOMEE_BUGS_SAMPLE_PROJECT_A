/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.execution;

import org.slf4j.Logger;

/**
 *
 * This is just a dummy class. We want to understand if when the class gets instantiated by CDI its state gets altered
 * on properties that should not have been affected by class instation. Namely, the myUslessState should be null.
 *
 *
 * @author b7godin
 */
public class DummyExecutionImplA implements DummyStringToStringExecution {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DummyExecutionImplA.class);

    String qualifierValue;

    /**
     * If a string is null transform it in the empty string.
     */
    @Override
    public String execute(String input) {
        if (qualifierValue != null) {
            LOGGER.error("Who exactly change my bean state from null to: {} when I was instantiated????? ",
                    qualifierValue);
        }
        return input == null ? "" : input;
    }
  

    @Override
    public void initialize(String qualifierValue) {
        this.qualifierValue = qualifierValue;
        LOGGER.info("The executioner {} is being initilized with qualifier value {} ", this.getClass().
                getCanonicalName(),
                qualifierValue);
    }

    @Override
    public Class<?> getExecutionerTypeKey() {
        return DummyStringToStringExecution.class;
    }

}
