package de.hsa.games.fatsquirrel.Core.Entity.Entities;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The BADPLANT Entity.
 * This Entity is very venomous.
 * If a Entity eats it, it will take 100 Energy damage
 * The BADPLANT doesn't move.
 */
public class BadPlant extends Entity{
    private final static int energy = -100;


    /**
     * Constructor for the BADPLANT
     * @param ID The unique ID as Int
     * @param xy The Coordinates as XY Object
     */
    public BadPlant(int ID, XY xy) {
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
