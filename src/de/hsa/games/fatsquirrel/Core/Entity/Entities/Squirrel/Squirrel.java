package de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Created by max on 23.05.16 09:33.
 */
public abstract class Squirrel extends Entity{
    /**
     * Create a Entity with the following Params
     * @param ID     The unique ID as Int
     * @param energy The Energy of the Entity as Int
     * @param xy     The Coordinate of the Entity as XY Object
     */
    protected Squirrel(int ID, int energy, XY xy) {
        super(ID, energy, xy);
    }

    @Override
    public void nextStep(EntityContext context) {

    }

    @Override
    public void nextStepRandomMove() {
        //Do Nothing
    }
}
