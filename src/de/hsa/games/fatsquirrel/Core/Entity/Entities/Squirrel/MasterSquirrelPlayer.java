package de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The MASTERSQUIRREL Entity.
 * Start Energy is 1000
 * Is Controlled by the Player or the Bot
 * Tries to collect as much Energy as possible
 * Game over when Energy is <= 0
 */
//TODO Game over wenn Energy kleiner gleich 0
public class MasterSquirrelPlayer extends MasterSquirrel {
    private XY vector;

    public MasterSquirrelPlayer(int ID, XY xy) {
        super(ID, xy);
    }


    /**
     * Set the Direction where the Squirrel walks
     * @param vector The XY  Object with the Vector
     */
    public void setMovementVector(XY vector) {
        this.vector = vector;
    }


    /**
     * The nextStep checks at first if the Master Squirrel is allowed to move
     * if true then it calls the Try move which contains all Rules
     * @param context The Information about the Game in form of the FlattenedBoard
     */
    @Override
    public void nextStep(EntityContext context) {
        if (getRounds() != 0) {
            updateTimeOut();
            return;
        }

        context.tryMove(this, vector);
        vector = null;
    }
}
