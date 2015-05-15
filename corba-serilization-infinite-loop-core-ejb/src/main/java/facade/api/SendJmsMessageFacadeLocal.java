/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.api;

import javax.ejb.Local;

/**
 *
 * @author b7godin
 */
@Local
public interface SendJmsMessageFacadeLocal {

    /**
     * send a jms message to Cran30AvSendQueue
     */
    void sendJmsMessage(String message);
}
