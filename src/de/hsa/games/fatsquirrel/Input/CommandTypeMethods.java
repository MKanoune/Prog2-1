package de.hsa.games.fatsquirrel.Input;

import de.hsa.games.fatsquirrel.Core.Board.Board;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrelPlayer;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import org.apache.log4j.Logger;

/**
 * The Methods for all Commands
 */
public class CommandTypeMethods {
    private final MasterSquirrelPlayer MS;
    private final Board board;


    /**
     * Constructor for the CommandTypeMethods
     * @param MS The MasterSquirrelPlayer which is to move
     * @param board The actual Board
     */
    public CommandTypeMethods(MasterSquirrelPlayer MS, Board board) {
        this.MS = MS;
        this.board = board;
    }

    public void right() {
        MS.setMovementVector(new XY(1, 0));
    }

    public void left() {
        MS.setMovementVector(new XY(-1, 0));
    }

    public void down() {
        MS.setMovementVector(new XY(0, 1));
    }

    public void up() {
        MS.setMovementVector(new XY(0, -1));
    }

    public void spawn(int energy) {
        board.flatten().spawnChildBot(MS, energy, new XY(1, 1));
    }

    public void help() {
        String output = "";
        for(int i = 0; i < CommandType.values().length; i++) {
            output += CommandType.values()[i].getName();
            output += CommandType.values()[i].getHelpText();
            output += "\n";
        }
        System.out.println(output);
    }

    public void exit() {
        System.exit(0);
    }

    public void energy() {
        System.out.println(MS.getEnergy());
    }
}
