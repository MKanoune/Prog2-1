package de.hsa.games.fatsquirrel.Core.Board;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.BadBeast;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.GoodBeast;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Squirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Handles the most important Methods to move the Entities
 */
public interface EntityContext {
    XY getSize();

    void tryMove(MiniSquirrelBot miniSquirrel, XY moveDirection);
    void tryMove(MasterSquirrel masterBot, XY moveDirection);
    void tryMove(GoodBeast goodBeast, XY moveDirection);
    void tryMove(BadBeast badBeast, XY moveDirection);

    void kill(Entity entity);
    void killAndReplace(Entity entity);

    EntityType getEntityType(XY pos);
    Entity getEntity(XY pos);

    Entity getNearestPlayerEntity(XY pos);
    void spawnChildBot(MasterSquirrel parent, int energy, XY direction);

    void allahuAkbar(Entity[][] view, MiniSquirrelBot miniSquirrelBot);

    XY getViewLowerLeft(Squirrel squirrel, XY visibility);
    XY getViewUpperRight(Squirrel squirrel, XY visibility);
}
