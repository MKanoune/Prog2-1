package de.hsa.games.fatsquirrel.Core;

import de.hsa.games.fatsquirrel.Core.Board.Board;
import de.hsa.games.fatsquirrel.Core.Display.UI;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrelPlayer;
import de.hsa.games.fatsquirrel.Input.Command;
import de.hsa.games.fatsquirrel.Input.CommandTypeMethods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implements the Game. Manage all Methods that Game provides
 */
public class GameImpl extends Game {
    private final MasterSquirrelPlayer MS;
    private final Board board;
    private final UI ui;


    /**
     * Constructor for GameImpl
     * @param s The actual State of the Game
     * @param ui The used UI for the Game
     */
    public GameImpl(State s, UI ui) {
        super(s);
        board = s.flattenedBoard().getBoard();
        this.ui = ui;
        MS = board.getPlayer();
    }


    /**
     * Renders the 2D Board
     */
    @Override
    void render() {
        ui.render(state.flattenedBoard());
    }


    /**
     * Get the actual Command and if it's a right Input the correlate Methods is called
     */
    @Override
    void processInput() {
        Command command = ui.getCommand();

        if(command == null) {
            return;
        }

        Object[] params = command.getParams();

        try{
            Class cl = Class.forName("de.hsa.games.fatsquirrel.Input.CommandTypeMethods");
            Method method = cl.getDeclaredMethod(command.getCommandType().getName(), command.getCommandType().getParamTypes());
            method.invoke(new CommandTypeMethods(MS, board), params);
        }
        catch (IllegalArgumentException e) {System.out.println("0");} //TODO hier kommt ein Fehler bei Spawn 100
        catch (ClassNotFoundException e){System.out.println("1");}
        catch (IllegalAccessException e){System.out.println("2");}
        catch (NoSuchMethodException e){System.out.println("3");}
        catch (InvocationTargetException e){System.out.println("4");}
    }
}
