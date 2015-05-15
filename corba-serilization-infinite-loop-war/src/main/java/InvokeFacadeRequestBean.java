
import facade.api.ModelCrudFacadeRemote;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import db.model.SomeEntity;

@RequestScoped
@Named(value = "invokeFacade")
public class InvokeFacadeRequestBean {
    private static final Logger LOGGER = Logger.getLogger(SendJmsMessageRequestBean.class.getCanonicalName());
    private static final String CLIENT_ID_GLOBAL = null;

    // ///////////////////////////////////////////////
    // BEGIN: STATE
    // ///////////////////////////////////////////////
    private String message = "DEFAULT-JMS-MESSAGE";

    /**
     * Try to make sure the ejb injected is remote
     */
    @EJB(lookup = "java:global/corba-serilization-infinite-loop-war/ModelCrudFacadeImpl!facade.api.ModelCrudFacadeRemote")
    //2015-05-13 21:06:54.931 INFO     60 EJB5181:Portable JNDI names for EJB ModelCrudFacadeImpl: [java:global/corba-serilization-infinite-loop-war/ModelCrudFacadeImpl, java:global/corba-serilization-infinite-loop-war/ModelCrudFacadeImpl!facade.api.ModelCrudFacadeRemote] (javax.enterprise.system.container.ejb.com.sun.ejb.containers) 
    private transient ModelCrudFacadeRemote modelCrudFacadeRemote;

    @Inject
    private transient FacesContext facesContext;

    private int treeDepth = 10;

    // ///////////////////////////////////////////////
    // BEGIN: ACTION
    // ///////////////////////////////////////////////

    /** form submission */
    public void execute() {
        try {
            // JMS transaction to put a message on a queue
            int idOfRecursiveEntity = modelCrudFacadeRemote.createInfiniteRecursiveEntity(treeDepth);
            SomeEntity someEntity = modelCrudFacadeRemote.fetchEntityById(idOfRecursiveEntity);

            // just some trivial info message to the screen
            facesContext.addMessage(CLIENT_ID_GLOBAL, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Created Entity with Id" + someEntity.getId(),
                    null));
        } catch (Exception e) {
            facesContext.addMessage(CLIENT_ID_GLOBAL, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.
                    getStackTrace().toString()));
        }
    }

    // ///////////////////////////////////////////////
    // BEGIN: BOILER PLATE CODE
    // ///////////////////////////////////////////////
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

}
