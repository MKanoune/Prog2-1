package de.hsa.games.fatsquirrel.Core.Display;

import de.hsa.games.fatsquirrel.Core.Board.BoardView;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import de.hsa.games.fatsquirrel.Input.Command;
import de.hsa.games.fatsquirrel.Input.CommandScanner;
import de.hsa.games.fatsquirrel.Input.CommandType;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Provides a Interface on the Console for support and Testing
 */
public class ConsoleUI implements UI{
    private static final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));


    /**
     * Get a Command from the Console Input
     * @return The Command to the given input
     */
    public Command getCommand() {
        CommandScanner commandScanner = new CommandScanner(CommandType.values(), inputReader);
        Command lastCommand = commandScanner.next();
        return lastCommand;
    }


    /**
     * Renders the Board on the Console
     * At first all MasterSquirrels Stats are printed
     * Afterwards the Board is printed with the Name of the Entities given in EntityType
     * @param view The BoardView
     */
    @Override
    public void render(BoardView view) {
        XY size = view.getSize();

        for (int i = 0; i < size.getY(); i++) {
            for (int j = 0; j < size.getX(); j++) {
                if (view.getEntityType(new XY(j, i)) == EntityType.MASTERSQUIRREL) {
                    Entity masterBot = view.getEntity(new XY(j, i));
                    System.out.println(masterBot.toString());
                }
            }
        }
        for (int i = 0; i < size.getY(); i++) {
            for (int j = 0; j < size.getX(); j++) {
                System.out.print(view.getEntityType(new XY(j, i)).name);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
