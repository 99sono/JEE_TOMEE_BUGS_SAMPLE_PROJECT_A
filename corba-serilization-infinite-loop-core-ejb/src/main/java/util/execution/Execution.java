/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.execution;

/**
 * Executes something and returns something.
 *
 * @author b7godin
 */
public interface Execution<I, O> {

 
    Class<?> getExecutionerTypeKey();

    /**
     * Logs that an exeuciton is being initialized by a producer
     */
    void initialize(String qualifierValue);

    /**
     * execute whatever to produce whatever
     */
    O execute(I input);

}
