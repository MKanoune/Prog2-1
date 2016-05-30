package de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Created by max on 23.05.16 09:50.
 */
public abstract class MasterSquirrel extends Squirrel{
    private final static int energy = 1000;


    /**
     * Constructor for MasterSquirrels (Energy is the Standard Energy)
     * @param ID The unique ID of the Entity
     * @param xy The Spawn Coordinates as XY Object
     */
    protected MasterSquirrel(int ID, XY xy) {
        super(ID, energy, xy);
    }


    /**
     * Constructor for MiniSquirrels, Energy is custom
     * @param ID     The unique ID of the Entity as Int
     * @param energy The Energy from the MINISQUIRREL as Int
     * @param xy     The Spawn Coordinate as XY Object
     */
    public MasterSquirrel(int ID, int energy, XY xy) {
        super(ID, energy, xy);
        //TODO die übergebene XY auf die aktuelle drauftrechen weils nur ein vector ist
    }


    /**
     * Spawns a MINISQUIRREL from the Current MASTERSQUIRREL
     * @param miniID     The unique ID as Int
     * @param miniEnergy The start Energy as Int
     * @param direction  The Spawn Vector as XY Object
     * @return           The MINISQUIRREL as MASTERSQUIRREL Object
     */
    public Entity createSlave(int miniID, int miniEnergy, XY direction) {
        if(miniEnergy < 100) {
            miniEnergy = 100;
        }
        MiniSquirrelBot slave = new MiniSquirrelBot(miniID, miniEnergy, direction, this);
        updateEnergy(-miniEnergy);
        return slave;
    }


    /**
     * Check if the given MINISQUIRREL is a Child of this MASTERSQUIRREL
     * @param object The MINISQUIRREL
     * @return       Boolean Value if it is one or not
     */
    public boolean checkSlave(MiniSquirrelBot object) {
        return object.getMaster() == this;
    }

    @Override
    public void nextStep(EntityContext context) {

    }

    @Override
    public void nextStepRandomMove() {

    }
}
