package de.hsa.games.fatsquirrel.Core.Entity;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrel;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains all Entities in an ArrayList
 */
public class EntityContainer {
    private List<Entity> collection;
    private List<Entity> toDelete;
    private List<Entity> toAdd;
    private Logger logger = Logger.getLogger(EntityContainer.class.getName());


    /**
     * Constructor for the Container.
     * Initialize @collection and @toAdd
     */
    public EntityContainer() {
        collection = new ArrayList<>();
        toAdd = new ArrayList<>();
    }


    /**
     * Insert a new Entity in the Container
     * There is no check if the Entity is already in the Container!
     * @param entity The Entity which has to be inserted
     */
    public void insert(Entity entity) {
        toAdd.add(entity);
    }


    /**
     * Deletes one Specific Entity of the Container, the others move up
     * @param entity The Entity which has to be deleted
     */
    public void delete(Entity entity) {
        logger.log(Level.INFO, "delete:" + entity.toString());
        toDelete.add(entity);
    }


    /**
     * Start the NextStepand the nextStepRandom of all contained Entities
     * @param context The Information about the Game in form of the FlattenedBoard
     */
    public void startNextStep(EntityContext context) {
        toDelete = new ArrayList<>();

        collection.addAll(toAdd);

        toAdd = new ArrayList<>();

        for(Entity aCollection : collection) {
            if(toDelete.contains(aCollection)) {
                continue;
            }
            aCollection.nextStepRandomMove();
            aCollection.nextStep(context);
        }
        collection.removeAll(toDelete);
    }


    /**
     * Get the Entity at one specific Position
     * @param pos The XY Object which contains the Coordinates of a possible Entity
     * @return The Entity at this Position, if there is no it will return null
     */
    public Entity getEntity(XY pos) {
        for(Entity aCollection : collection) {
            if(aCollection.getXY() == pos) {
                return aCollection;
            }
        }
        return null;
    }


    /**
     * Get The Entity at the Position in the Array
     * @param pos The Int Value of the Position as Int
     * @return The Content at this Position as Entity
     */
    public Entity getEntity(int pos) {
        return collection.get(pos);
    }


    public Collection getCollection() {
        return collection;
    }

    public void addToAdd() {
        collection.addAll(toAdd);
        toAdd.removeAll(toAdd);
    }

    public void deleteAll() {
        toDelete = new ArrayList<>();

        toDelete.addAll(collection.stream().filter(aCollection -> !(aCollection instanceof MasterSquirrel)).collect(Collectors.toList()));

        collection.removeAll(toDelete);
    }
}
