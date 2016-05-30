package de.hsa.games.fatsquirrel.Core.Entity;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Contains all Entities unsorted in an Array
 */
public class EntityContainer {
    private final Entity[] container;
    private int pos = 0;


    /**
     * Constructor for the Container.
     * Based on the Size it's possible to calculate the max. of possible Entities (Length * Width)
     * @param size The Size of the Game Field as XY Object
     */
    public EntityContainer(XY size) {
        container = new Entity[size.getX()*size.getY()];
    }


    /**
     * Insert a new Entity in the Container
     * @Warning There is no check if the Entity is already in the Container!
     * @param entity The Entity which has to be inserted
     */
    public void insert(Entity entity) {
        container[pos] = entity;
        pos++;
    }


    /**
     * Deletes one Specific Entity of the Container, the others move up
     * @param entity The Entity which has to be deleted
     */
    public void delete(Entity entity) {
        System.out.println("\ndelete:" + entity.toString() + "\n");
        for(int i = 0; i < container.length; i++) {
            if (container[i] == entity) {
                pos = i;
                while (container[pos] != null) {
                    container[pos] = container[pos + 1];
                    pos++;
                }
                break;
            }
        }
    }


    /**
     * Start the NextStepand the nextStepRandom of all contained Entities
     * @param context The Information about the Game in form of the FlattenedBoard
     */
    public void startNextStep(EntityContext context) {
        for (Entity aContainer : container) {
            if (aContainer == null) continue;
            aContainer.nextStepRandomMove();
            aContainer.nextStep(context);
        }
    }


    /**
     * Get the Entity at one specific Position
     * @param pos The XY Object which contains the Coordinates of a possible Entity
     * @return The Entity at this Position, if the is no --> null
     */
    //TODO Das hier kosten viel REchenleistung vielleicht lieber im 2 dimensionalen Array direkt ansprechen?
    public Entity getEntity(XY pos) {
        for (Entity aContainer : container) {
            if (aContainer == null) continue;
            if (aContainer.getXY() == pos) {
                return aContainer;
            }
        }
        return null;
    }


    /**
     * Get The Entity at the Position in the Array
     * @param pos The Int Value of the Position as Int
     * @return    The Content at this Position as Entity
     */
    public Entity getEntity(int pos) {
        return container[pos];
    }
}
