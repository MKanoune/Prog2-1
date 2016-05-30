package de.hsa.games.fatsquirrel.Core.Display;

import de.hsa.games.fatsquirrel.Core.Board.BoardView;
import de.hsa.games.fatsquirrel.Input.Command;

/**
 * Manage the User Interface
 * Can return a the last given Command
 * Can get the next Command
 * Can render the Window
 */
public interface UI {
    Command getCommand();

    void render(BoardView view);
}
