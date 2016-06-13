package de.hsa.games.fatsquirrel.Core.Board;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Provides Information of the Board
 */
public interface BoardView {

    /**
     * Get the Size of the Board
     * @return The Size as XY Object (Width, Length)
     */
    XY getSize();


    /**
     * Get The Type of a Entity, The Types ar defined in EntityType
     * @see EntityType
     * @param xy The Coordinates of the Entity as XY Object
     * @return The Entity Type as EntityType Object
     */
    EntityType getEntityType(XY xy);

    /**
     * Get The Entity, when you only know the Position
     * @param pos The Position where you want to know the Entity
     * @return The Entity Object
     */
    Entity getEntity(XY pos);
}
