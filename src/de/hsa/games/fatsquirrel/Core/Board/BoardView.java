package de.hsa.games.fatsquirrel.Core.Board;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Provides Information of the Board
 */
public interface BoardView {
    XY getSize();

    EntityType getEntityType(XY xy);

    Entity getEntity(XY pos);
}
