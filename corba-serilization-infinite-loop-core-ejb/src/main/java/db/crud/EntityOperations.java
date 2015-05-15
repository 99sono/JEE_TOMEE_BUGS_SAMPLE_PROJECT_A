/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.crud;

import db.cdi.OrclDatabase;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import db.model.SomeEntity;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;

/**
 *
 * @author b7godin
 */
@ApplicationScoped
public class EntityOperations {

    private int executionNumber = 0;

    @Inject
    @OrclDatabase
    private EntityManager em;

    /**
     * @retun a dummy entity written to the db.
     */
    public SomeEntity create() {
        // Prepare abount 1KB of LOB DATA
        String currentExecution = getNextExecutionNumber().toString();
        String lobTextToPut = padRight("Execution Log Nr:  " + currentExecution + " DUMMY PADDING:", 70)
                + "EndPadding.";

        // persist an enetity
        SomeEntity entityToCreate = new SomeEntity();
        entityToCreate.setText(lobTextToPut);

        em.persist(entityToCreate);

        return entityToCreate;
    }

    public void updateEntityForver(SomeEntity entity) {

        // the text will grow bigger
        Integer currentUpdate = 1;
        String originalText = entity.getText();
        while (true) {
            // update entity
            String newText = originalText + currentUpdate.toString();
            entity.setText(newText);

            // force the flush and read for the DB
            // we want to maximize the proabbiliy of being driver code when the contarin kills us
            for (int i = 0; i < 1000; i++) {
                fetchAll();
            }

            currentUpdate++;
        }
    }

    /**
     * @return fetch all entities from the DB.
     *
     */
    public List<SomeEntity> fetchAll() {
        return em.createQuery("SELECT A From SomeEntity A WHERE 1=1").getResultList();
    }

    /**
     * fetch all ids
     */
    public List<Integer> fetchAllIds() {
        return em.createQuery("SELECT A.id From SomeEntity A WHERE 1=1").getResultList();
    }

    /**
     * @return fetch all entities from the DB.
     *
     */
    public Long countAll() {
        return (Long) em.createQuery("SELECT COUNT(A) From SomeEntity A WHERE 1=1").getSingleResult();
    }

    /**
     * @return fetch all entities from the DB.
     *
     */
    public void deleteAll() {
        List<Integer> allIds = fetchAllIds();
        for (Integer id : allIds) {
            em.remove(em.find(SomeEntity.class, id));
            System.out.println("Removed entity with Id: " + id);
        }
    }

    /**
     * create a super incorrect entity structure where we have an inifite refrence loop, where an etity is parent and
     * child for itself.
     */
    public int createInfiniteRecursiveEntity(int depthOfRecursion) {
        // we create the very first entity outside of the recursion
        // because the recrusion will give us the deepest entity of all
        SomeEntity rootOfTheTree = create();

        // so now we build a deep a tree where in each iteration a parent entity becomes related to a child entity
        SomeEntity deepestEntityOfRecursio = recursiveCreateInfiniteResursiveEntity(depthOfRecursion, 1, rootOfTheTree);

        // finally we do a magic trick - to make sure that CORBA will have a hard time
        // we finish up our hiearchy of entities by making sure that our rootOfTheTree
        // is a child of the deepest entity of all
        // meaning the tree is truly infinite if our tree has depth 3
        // A -> B -> C this final step ensures that we make A -> B -> C -> A
        rootOfTheTree.setParent(deepestEntityOfRecursio);
        deepestEntityOfRecursio.setChildren(new ArrayList<SomeEntity>(Arrays.asList(rootOfTheTree)));

        // time to flush to get the id of our entity root
        em.flush();
        return rootOfTheTree.getId();
    }

    public SomeEntity recursiveCreateInfiniteResursiveEntity(final int maxDepthOfEntityHierarchy, int currentDepth,
            final SomeEntity parentEntity) {
        boolean itLastRecursiveStep = currentDepth >= maxDepthOfEntityHierarchy;
        if (itLastRecursiveStep) {
            // ok we're done we've gone as deep as we are allowed to go
            // we stop building a depp recursive hierarchy and simply return
            // the last entity that was produced (the deepest one)
            return parentEntity;
        }

       // we have to continue building up the hiearchy
        // so we create a new entity and setup a parent-child relationship
        // where parent knows child and child knows parent
        SomeEntity currentNewEntity = create();
        currentNewEntity.setParent(parentEntity);
        boolean thisIsNotTheFirstIteration = parentEntity != null;
        if (thisIsNotTheFirstIteration) {
            parentEntity.setChildren(new ArrayList<SomeEntity>(Arrays.asList(currentNewEntity)));
        }

        // now we continue building up the recursive tree until we've gone as deep as we are allowed
        return recursiveCreateInfiniteResursiveEntity(maxDepthOfEntityHierarchy, currentDepth + 1, currentNewEntity);

    }

    /**
     * fetches an entity by id
     */
    public SomeEntity fetchEntityById(Integer id) {
        return em.find(SomeEntity.class, id);
    }

    // //////////////////////
    // BEGIN: BOILER PLATE CODE
    // //////////////////////
    private synchronized Integer getNextExecutionNumber() {
        return Integer.valueOf(++executionNumber);
    }

    public String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}
