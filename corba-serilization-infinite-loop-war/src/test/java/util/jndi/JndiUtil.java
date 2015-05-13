/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.jndi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author b7godin
 */
public final class JndiUtil {

    public static final JndiUtil SINGLETON = new JndiUtil();

    InitialContext ictx = GlassfishContextFactory.SINGLETON.create();
    private JndiUtil() {
    }

    public <T> T resolveBean(String war, String beanName, Class<T> remoteInterface) {
        /* Object node = null;
         try {
            node = ictx.lookup("java:global/");
        } catch (NamingException ex) {
            Logger.getLogger(JndiUtil.class.getName()).log(Level.SEVERE, null, ex);
        } */
        String name = "java:global/" + war + "/" + beanName + "!" + remoteInterface.getName();
        T bean = null;
        try {
            return remoteInterface.cast(ictx.lookup(name));
        } catch (NamingException ne) {
            throw new RuntimeException("Failed to resolved bean with JNDI name: " + name, ne);
        }
    }

}
