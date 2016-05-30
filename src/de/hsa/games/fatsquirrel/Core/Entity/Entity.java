package de.hsa.games.fatsquirrel.Core.Entity;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Manages all Entities
 */
public abstract class Entity{
    private final int ID;
    private int energy;
    private final XY xy;
    private int rounds = 0;


    /**
     * Create a Entity with the following Params
     * @param ID     The unique ID as Int
     * @param energy The Energy of the Entity as Int
     * @param xy     The Coordinate of the Entity as XY Object
     */
    protected Entity(int ID, int energy, XY xy) {
        this.ID = ID;
        this.energy = energy;
        this.xy = xy;
    }


    /**
     * Updates the Energy of the Entity by delta
     * @param delta The Energy as Int that has to added or subtracted
     */
    public void updateEnergy(int delta) {
        energy += delta;
    }


    /**
     * Get the ID of the Current Entity
     * @return The ID as Int
     */
    public int getID() {
        return ID;
    }


    /**
     * Get the Energy of the current Entity
     * @return The current Energy as Int
     */
    public int getEnergy() {
        return energy;
    }


    /**
     * Get the Current Position
     * @return The XY Object which contains the Position
     */
    public XY getXY() {
        return xy;
    }


    /**
     * Get the Current Rounds.
     * The Rounds define if the Entity is allowed to move or not
     * If Rounds = 0 it is allowed to move
     * @return Rounds as Int
     */
    protected int getRounds() {
        return rounds;
    }


    /**
     * Set the Rounds how long the Entity has to time out
     * @param rounds The Int amount of Rounds
     */
    public void setTimeOut(int rounds) {
        this.rounds = rounds;
    }


    /**
     * Subtract one of the Rounds
     */
    protected void updateTimeOut() {
        rounds--;
    }


    /**
     * The NextStep of every Entity is unique, so it's defined in the specific Class
     * @param context The Information about the Game in form of the FlattenedBoard
     */
    public abstract void nextStep(EntityContext context);


    //TODO kommi
    public abstract void nextStepRandomMove();


    /**
     * Writes the Entity to a String with all Data
     * @return String with the Data
     */
    @Override
    public String toString() {
        int i = this.getClass().getName().lastIndexOf(".");
        return ("Name: " + this.getClass().getName().substring(i + 1) + " ID: " + ID + " Energy: " + energy + " Coordinates: " + xy.getX() + "/" + xy.getY());
    }
}
