package de.hsa.games.fatsquirrel.Core.Entity.Entities;

import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Movement.RandomMove;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The GOODBEAST Entity.
 * Hast 200 Energy, which the Hunter of this shy Beast gets when he manages to kill it
 * But it isn't that easy the GOODBEAST tries to run away from every Master and MINISQUIRREL.
 * Because of its weight is can only move every 4 Rounds
 */
public class GoodBeast extends Entity {
    private final static int energy = 200;
    private RandomMove rm;
    private int possibleMove;


    /**
     * Constructor of the GOODBEAST
     * @param ID The unique ID as Int
     * @param xy The Coordinate as XY Object
     */
    public GoodBeast(int ID, XY xy) {
        super(ID, energy, xy);
    }


    /**
     * The NextStep checks at first if it is allowed to move
     * After that it get a RandomMoveCommand, if it's null it cant move and it stand still
     * If not it checks if a Player is nearby
     * If not the Random move is executed in TryMove, if the destination isn't free it gets a new RandomMoveCommand
     * If a Player is nearby it tries to escape, if the first way is blockaded it tries 2 others and then stand still of Fear
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

        Entity nearestPlayer = context.getNearestPlayerEntity(getXY());
        if (nearestPlayer == null) {
            context.tryMove(this, moveCommand);
        } else {
            possibleMove++;
            if (possibleMove == 4) {
                return;
            }
            context.tryMove(this, escape(nearestPlayer.getXY(), possibleMove));
        }
        setTimeOut(4);
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
     * The Escape Method, escapes from the given nearestPlayer.
     * The Possible moves say in which of the three directions the GOODBEAST escapes.
     * @param nearestPlayer The XY Object with the Coordinates of the Entity it want to escape
     * @param possibleMove One of the three Int Values in which Direction the escape is
     * @return A XY Object with the Vector from the nearestPlayer away
     */
    private XY escape(XY nearestPlayer, int possibleMove) {
        int moveX, moveY;
        XY vector = getXY().getVector(nearestPlayer);

        if(possibleMove == 1) {
            if (vector.getX() > 0) moveX = -1;
            else if (vector.getX() < 0) moveX = 1;
            else moveX = 0;

            if (vector.getY() > 0) moveY = -1;
            else if (vector.getY() < 0) moveY = 1;
            else moveY = 0;
        }
        else if(possibleMove == 2) {
            if (vector.getX() > 0) moveX = 0;
            else if (vector.getX() < 0) moveX = 1;
            else moveX = 0;

            if (vector.getY() > 0) moveY = 0;
            else if (vector.getY() < 0) moveY = 1;
            else moveY = 0;
        }
        else {
            if (vector.getX() > 0) moveX = -1;
            else if (vector.getX() < 0) moveX = 0;
            else moveX = 0;

            if (vector.getY() > 0) moveY = -1;
            else if (vector.getY() < 0) moveY = 0;
            else moveY = 0;
        }

        return new XY(moveX, moveY);
    }
}
