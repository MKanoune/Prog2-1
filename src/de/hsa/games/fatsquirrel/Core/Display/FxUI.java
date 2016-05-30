package de.hsa.games.fatsquirrel.Core.Display;

import de.hsa.games.fatsquirrel.Core.Board.BoardView;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.*;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrelPlayer;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Game;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import de.hsa.games.fatsquirrel.Input.Command;
import de.hsa.games.fatsquirrel.Input.CommandType;
import de.hsa.games.fatsquirrel.Input.CommandTypeInfo;
import de.hsa.games.fatsquirrel.Launcher;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The UserInterface for the Graphical display
 * CELL_SIZE defines how big one Entity or Coordinate Field is in px
 */
public class FxUI extends Scene implements UI {
    private static final int CELL_SIZE = 15;
    private static final CommandTypeInfo[] commandTypeInfos = CommandType.values();
    private static Command lastCommand;
    private final Canvas boardCanvas;
    private final Label msgLabel;


    private FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
        super(parent);
        this.boardCanvas = boardCanvas;
        this.msgLabel = msgLabel;
    }


    public static FxUI createInstance(XY boardSize) {
        Canvas boardCanvas = new Canvas(boardSize.getX() * CELL_SIZE, boardSize.getY() * CELL_SIZE);
        Label statusLabel = new Label();
        VBox top = new VBox();
        top.getChildren().add(boardCanvas);
        top.getChildren().add(statusLabel);
        statusLabel.setText("Wer ist dieser Java?");

        final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel);
        fxUI.setOnKeyPressed(event -> {
                    String c = event.getText();
                    c.toLowerCase();
                    statusLabel.setText("last input: " + c);

                    if (c.equals("t")) {
                        Launcher.startCommand(); //TODO Spiel anhalten
                    }

                    switch (c) {
                        case "w":
                            lastCommand = new Command(CommandType.UP, null);
                            break;
                        case "a":
                            lastCommand = new Command(CommandType.LEFT, null);
                            break;
                        case "s":
                            lastCommand = new Command(CommandType.DOWN, null);
                            break;
                        case "d":
                            lastCommand = new Command(CommandType.RIGHT, null);
                    }
                }
        );
        return fxUI;
    }


    @Override
    public Command getCommand() {
        Command tmp = lastCommand;
        lastCommand = null;
        return tmp;
    }


    @Override
    public void render(BoardView view) {
        Platform.runLater(() -> repaintBoardCanvas(view));
    }


    public void repaintBoardCanvas(BoardView view) {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        XY viewSize = view.getSize();
        int xCord = 0;
        int yCord = 0;
        for (int x = 0; x < viewSize.getX(); x++) {
            for (int y = 0; y < viewSize.getY(); y++) {
                Entity e = view.getEntity(new XY(x, y));
                fill(e, gc, xCord, yCord);
                yCord += CELL_SIZE;
            }
            yCord = 0;
            xCord += CELL_SIZE;
        }
    }

    private void fill(Entity e, GraphicsContext gc, int xCord, int yCord) {
        if (e instanceof Wall) {
            gc.setFill(Color.GRAY);
            gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof BadPlant) {
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof BadBeast) {
            gc.setFill(Color.INDIANRED);
            gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof GoodPlant) {
            gc.setFill(Color.GREENYELLOW);
            gc.fillRect(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof GoodBeast) {
            gc.setFill(Color.YELLOW);
            gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof MasterSquirrelPlayer) {
            gc.setFill(Color.ROYALBLUE);
            gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof MasterSquirrelBot) {
            gc.setFill(Color.RED);
            gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
        } else if (e instanceof MiniSquirrelBot) {
            gc.setFill(Color.BLUE);
            gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
        }
    }


    public void message(final String msg) {
        Platform.runLater(() -> msgLabel.setText(msg));
    }

    public static void displayForCommands() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Commandline");

        Label label = new Label("Enter your Command");

        VBox layout = new VBox();

        TextField textField = new TextField();

        Button submitCommand = new Button();
        submitCommand.setText("Submit");
        submitCommand.setDefaultButton(true);
        submitCommand.setOnAction(e -> {
            String input = textField.getText();

            if (!input.contains(" ")) {
                for (CommandTypeInfo commandTypeInfo : commandTypeInfos) {
                    String tmpName = commandTypeInfo.getName();
                    if (tmpName.equals(input)) {
                        lastCommand = new Command(commandTypeInfo, null);
                    }
                }
            } else {
                for (CommandTypeInfo commandTypeInfo : commandTypeInfos) {
                    String tmpName = commandTypeInfo.getName();
                    Class<?>[] paramTypes = commandTypeInfo.getParamTypes();

                    String[] inputSplit = input.split(" ");
                    Object[] param;
                    param = new Object[commandTypeInfo.getParamTypes().length];

                    for(int i = 0; i < commandTypeInfo.getParamTypes().length; i++) {
                        param[i] = Integer.parseInt(inputSplit[i+1]);
                    }

                    if (tmpName.equals(inputSplit[0])) {
                        if (paramTypes[0] == int.class) {
                            lastCommand = new Command(commandTypeInfo, param);
                        }
                    }
                }
            }
            window.close();
        });

        layout.getChildren().addAll(label, textField, submitCommand);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }
}
