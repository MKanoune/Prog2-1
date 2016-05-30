package de.hsa.games.fatsquirrel.Core.Entity.Entities;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The WALL Entity.
 * Energy is -10
 * Does nothing because it is a WALL!
 */
public class Wall extends Entity {
    private final static int energy = -10;


    /**
     * Constructor of the WALL
     * @param id The unique ID of the Entity as Int
     * @param xy The Coordinates as XY Object
     */
    public Wall(int id, XY xy) {
        super(id, energy, xy);
    }

    @Override
    public void nextStep(EntityContext context) {
        //Do nothing
    }

    @Override
    public void nextStepRandomMove() {
        //Do Nothing
    }
}
