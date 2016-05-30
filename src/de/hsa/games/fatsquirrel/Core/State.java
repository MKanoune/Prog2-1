package de.hsa.games.fatsquirrel.Core;

import de.hsa.games.fatsquirrel.Core.Board.Board;
import de.hsa.games.fatsquirrel.Core.Board.FlattenedBoard;

/**
 * The Actual State of the Game
 */
public class State {
    int highscore; //TODO Highscore machen
    private final Board board;


    /**
     * Constructor for the State
     * @param board The Board
     */
    public State(Board board){
        this.board = board;
    }


    /**
     * Update the State
     */
    public void update() {
        board.update();
    }


    /**
     * Refresh the Board, kind of a Update
     * @return The FlattenedBoard
     */
    public FlattenedBoard flattenedBoard() {
        return board.flatten();
    }
}
