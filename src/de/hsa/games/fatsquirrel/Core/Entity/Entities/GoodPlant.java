package de.hsa.games.fatsquirrel.Core.Entity.Entities;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The GOODPLANT Entity
 * Does Nothing is only standing there and doing nothing but photosynthesis
 * Provides 100 Energy to the one who eat it
 */
public class GoodPlant extends Entity {
    private final static int energy = 100;


    /**
     * Constructor for the GOODPLANT
     * @param ID The unique ID as Int
     * @param xy The Coordinates as XY Object
     */
    public GoodPlant(int ID, XY xy) {
        super(ID, energy, xy);
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
