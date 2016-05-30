package de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

@Deprecated
/**
 * The MINISQUIRREL Entity
 * Is a child of the MASTERSQUIRREL and can be spawned by it
 * The min. Energy is 100, the Energy is withdrawn by the Master
 * Collect Energy and brings it to the Master
 */
public class MiniSquirrel extends Entity {
    private final MasterSquirrel master;


    /**
     * Constructor of the MINISQUIRREL
     * @param miniID     The unique ID of the MINISQUIRREL as Int
     * @param miniEnergy The Energy it gets from the Master as Int
     * @param direction  The Direction Vector where it spawns next to the Master as XY Object
     * @param master     The Entity of the Master, to refer to is later
     */
    MiniSquirrel(int miniID, int miniEnergy, XY direction, MasterSquirrel master) {
        super(miniID, miniEnergy, direction);
        this.master = master;
    }


    /**
     * Get the Master so it can search for him
     * @return The Master as MASTERSQUIRREL
     */
    public MasterSquirrel getMaster() {
        return master;
    }


    /**
     * Does nothing tll yet
     * @param context The Information about the Game in form of the FlattenedBoard
     */
    @Override
    public void nextStep(EntityContext context) {
        if (getRounds() != 0) {
            updateTimeOut();
            return;
        }
        updateEnergy(-1);
    }

    @Override
    public void nextStepRandomMove() {
        //Do Nothing
    }
}
