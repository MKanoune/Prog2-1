package de.hsa.games.fatsquirrel.Core.Entity.Entities;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.RandomMove;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The BADBEAST Entity.
 * The BADBEAST tries to bite any nearby Squirrel. After 7 bites it has no teeth any more and dies.
 * Every Bite provides 150 damage.
 * It can only move every 4 rounds
 */
public class BadBeast extends Entity{
    private final static int energy = -150;
    private int bites = 7;
    private RandomMove rm;
    private int possibleMove = 0;


    /**
     * The Constructor for the BADBEAST
     * @param ID The unique ID as Int
     * @param xy The Coordinates as XY Object
     */
    public BadBeast(int ID, XY xy) {
        super(ID, energy, xy);
    }


    /**
     * The nextStep first checks if the BADBEAST is allowed to move.
     * If true it gets a random move Command, if all moveCommands are tried the BADBEAST won't move
     * If false it checks if a Squirrel is nearby.
     * If false the random move will be executed
     * If true the moveToTarget Method is executed
     * After an executed Move the TimeOut is set to 4
     * @param context The Information about the Game in form of the FlattenedBoard
     */
    @Override
    public void nextStep(EntityContext context) {
        if (getRounds() != 0) {
            updateTimeOut();
            return;
        }

        XY moveCommand = rm.getRandomMoveCommand();

        if (moveCommand == null) return;

        Entity nearestPlayer = context.getNearestPlayerEntity(this.getXY());
        if (nearestPlayer == null) {
            context.tryMove(this, moveCommand);
        } else {
            possibleMove++;
            if (possibleMove == 4) {
                return;
            }
            context.tryMove(this, moveToTarget(nearestPlayer.getXY(), possibleMove));
        }
        setTimeOut(4);
        possibleMove = 0;
    }


    /**
     * Initialize a new Random move and set the possibleMoves to 0.
     * Because every Entity needs their own and every round new one this Method is called before the nextStep
     */
    @Override
    public void nextStepRandomMove() {
        rm = new RandomMove();
        possibleMove = 0;
    }


    /**
     * Subtract one of the bites the BADBEAST have.
     */
    public void updateBites() {
        bites--;
    }


    /**
     * Get the left Bites of the BADBEAST
     * @return The Bites as Int
     */
    public int getBites() {
        return bites;
    }


    /**
     * This Method moves to an given Player, if one move cant be executed two more options ar possible.
     * @param nearestPlayer The XY Coordinates of the Player it wants to move
     * @param possibleMove The left Possibilities to move to the Target
     * @return A Vector that an be executed as XY Object
     */
    private XY moveToTarget(XY nearestPlayer, int possibleMove) {
        int moveX, moveY;
        XY vector = getXY().getVector(nearestPlayer);

        if(possibleMove == 1) {
            if (vector.getX() > 0) moveX = 1;
            else if (vector.getX() < 0) moveX = -1;
            else moveX = 0;

            if (vector.getY() > 0) moveY = 1;
            else if (vector.getY() < 0) moveY = -1;
            else moveY = 0;
        }
        else if(possibleMove == 2) {
            if (vector.getX() > 0) moveX = 0;
            else if (vector.getX() < 0) moveX = -1;
            else moveX = 0;

            if (vector.getY() > 0) moveY = 0;
            else if (vector.getY() < 0) moveY = -1;
            else moveY = 0;
        }
        else {
            if (vector.getX() > 0) moveX = 1;
            else if (vector.getX() < 0) moveX = 0;
            else moveX = 0;

            if (vector.getY() > 0) moveY = 1;
            else if (vector.getY() < 0) moveY = 0;
            else moveY = 0;
        }

        return new XY(moveX, moveY);
    }
}
