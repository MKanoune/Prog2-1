package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.Core.Board.Board;
import de.hsa.games.fatsquirrel.Core.Display.ConsoleUI;
import de.hsa.games.fatsquirrel.Core.Display.FxUI;
import de.hsa.games.fatsquirrel.Core.Display.UI;
import de.hsa.games.fatsquirrel.Core.Game;
import de.hsa.games.fatsquirrel.Core.GameImpl;
import de.hsa.games.fatsquirrel.Core.State;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Launches the Application
 */
public class Launcher extends Application{
    private static Game game;
    private final static Logger log = Logger.getLogger(Launcher.class.getName());

    public static void main(String[] args) throws InterruptedException {
        int or = 1;

        switch(or) {
/***************************************ConsoleUI***************************************/
            case 0:
                log.info("Start Console Output");
                UI ui = new ConsoleUI();
                Board b = new Board();
                State s = new State(b);
                Game g = new GameImpl(s, ui);
                g.run();

/***************************************FxUI***************************************/
            case 1:
                log.info("Start Fx Output");
                Application.launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Board board = new Board();

        FxUI fxUI = FxUI.createInstance(board.getSize());
        primaryStage.setScene(fxUI);
        primaryStage.setTitle("Diligent Squirrel");
        fxUI.repaintBoardCanvas(board.flatten());

        game = new GameImpl(new State(board), fxUI);

        Timer timer = new Timer();
        timer.schedule(new Task(), 0);

        fxUI.getWindow().setOnCloseRequest(event -> System.exit(-1));
        primaryStage.show();
    }

    private static class Task extends TimerTask {
        @Override
        public void run() {
            try {
                startGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void startGame() throws InterruptedException {
            game.run();
        }
    }

    public static void startCommand() {
        FxUI.displayForCommands();
    }
}
