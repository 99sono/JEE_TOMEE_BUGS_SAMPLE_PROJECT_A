package db.setup;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


/**
 * Session Bean implementation class StartEJB
 *
 * We do not want a container manager transaction here. We want the call to our special entity manager to produce a
 * database on startup.
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NEVER)
public class CreateDatabase {

    /**
     * A entity manager where commits can safely be done without the container complaining that the transactions are
     * managed by contianer. It allows for the creation of the DB schema. Uses a NON XA NON CONTAINER MANAGED entity
     * manager.
     */
    @PersistenceUnit(unitName = "CreateDbPU")
    private EntityManagerFactory emf;


    @PostConstruct
    public void startTestcase() {
        EntityManager em = emf.createEntityManager();
        try {
            // em.getTransaction().begin();
            System.out.println("Creating databse - PM is being created for the first time - we commit no changes");
            // the call to EM.isOpen will automatically create the dummy database with commits done behind our back so
            // we do not even open or close a transaction
            em.isOpen();
            System.out.println("Ending create database.");
            // em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Default constructor.
     */
    public CreateDatabase() {
    }

}
