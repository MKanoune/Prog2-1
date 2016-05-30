package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Created by max on 23.05.16 09:22.
 */
public interface ControllerContext {
    XY getViewLowerLeft();
    XY getViewUpperRight();
    Entity getEntity(XY pos);
    void move(XY direction);
    void spawnMiniBot(XY direction, int energy);
    int getEnergy();
    XY getBotPosition();
}
