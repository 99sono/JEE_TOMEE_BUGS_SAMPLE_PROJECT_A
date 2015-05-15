/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade.impl;

import facade.api.SendJmsMessageFacadeLocal;
import inteceptor.FacadeInterceptor;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import util.SendJMSMessageUtilBean;


/**
 *
 * @author b7godin
 */
@Stateless
@Interceptors(FacadeInterceptor.class)
public class SendJmsMessageFacade implements SendJmsMessageFacadeLocal {

    @Resource(mappedName = "jms/JcomJmsFactory")
    private transient ConnectionFactory connectionFactory;
    @Resource(lookup = "queue/Cran30AvSendQueue")
    private transient Queue queue;

    @Inject
    transient private SendJMSMessageUtilBean sendJMSMessageUtilBean;

    /**
     * puts the desired message on the Cran30AvSendQueue
     */
    @Override
    public void sendJmsMessage(String message) {
        sendJMSMessageUtilBean.sendToQueue(connectionFactory, queue, message);
    }

}
