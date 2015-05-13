package util;


import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;



/**
 *
 * @author b7godin
 */
@ApplicationScoped
public class SendJMSMessageUtilBean {

    private static final Logger LOGGER = Logger.getLogger(SendJMSMessageUtilBean.class.getCanonicalName());
    

    public void sendToQueue(ConnectionFactory connectionFactory, Queue sendQueue, String message) {
        Connection connection = null;
        Session session = null;

        try {

            connection = connectionFactory.createConnection();
            session = connection.createSession(true, javax.jms.Session.SESSION_TRANSACTED);

            MessageProducer messageProducer = ((Session) session).createProducer(sendQueue);

            TextMessage textMessage = session.createTextMessage(message);

            messageProducer.send(textMessage);

        } catch (JMSException e) {
            throw new RuntimeException("unkown error in jms", e);
        } finally {
            closeSession(session);
            closeConnection(connection);
        }
    }

    private void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                LOGGER.log(java.util.logging.Level.SEVERE, "exception while closing session to jms.", e);
            }
        }
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                LOGGER.log(java.util.logging.Level.SEVERE, "error closing connection", e);
            }
        }
    }
   
}
