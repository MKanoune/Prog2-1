package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * All Methods to control the Bots
 */
public interface ControllerContext {
    /**
     * Gets the max. View to the Lower Left
     * @return The Coordinates of the max. view as XY Object
     */
    XY getViewLowerLeft();

    /**
     * Gets the max. View to the upper Right
     * @return The Coordinates of the max. view as XY Object
     */
    XY getViewUpperRight();

    /**
     * Gets the Entity at a Position
     * @param pos The Position of the wanted Entity as XY Object
     * @return The Entity at this Position
     */
    Entity getEntity(XY pos);

    /**
     * Moves in a specific Direction (only 1 Field per move)
     * @param direction The Direction as XY Object
     */
    void move(XY direction);

    /**
     * spawns a Mini Bot for the MasterSquirrel
     * @param direction The spawn direction from the MasterSquirrel away as XY Object
     * @param energy The Energy which the MiniSquirrel has at the Start min. is 100
     */
    void spawnMiniBot(XY direction, int energy);

    /**
     * Gets The Energy of a Squirrel
     * @return The Energy as Int
     */
    int getEnergy();

    /**
     * Get The actual Position of this Bot
     * @return The XY Coordinates
     */
    XY getBotPosition();
}
