package de.hsa.games.fatsquirrel.Core.Board;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.*;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityContainer;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * Manager for the Board, its kind of a Interface between the FlattenedBoard and other Classes
 */
public class Board {
    private final XY size;
    private final EntityContainer container;
    private int ID = 0;


    /**
     * Constructor for the Board
     * Generates all start Entities and insert them in the Container
     * At first a Big WALL around the field is generated
     * After that it gets the start Values, for how much Entities are needed from the BoardConfig.
     * Then it spawns them at a random Position on the field
     */
    public Board() {
        BoardConfig config = new BoardConfig();
        size = new XY(config.WIDTH, config.HEIGHT);
        container = new EntityContainer(size);

/************************************************WALLS************************************************/
        for (int x = 0; x < size.getX(); x++) { //Build from left to Right
            container.insert(new Wall(getNextID(), new XY(x, 0)));
        }
        for (int y = 1; y < size.getY() - 1; y++) { //Build from the right Corner down
            container.insert(new Wall(getNextID(), new XY(size.getX() - 1, y)));
        }
        for (int x = size.getX() - 1; x >= 0; x--) { //Build from left to right
            container.insert(new Wall(getNextID(), new XY(x, size.getY() - 1)));
        }
        for (int y = size.getY() - 2; y >= 1; y--) { //build from left down corner up
            container.insert(new Wall(getNextID(), new XY(0, y)));
        }

/************************************************BADBEASTS************************************************/
        for (int i = 0; i < config.BADBEAST; i++) {
            container.insert(new BadBeast(getNextID(), getRandomPos()));
        }

/************************************************GOODBEASTS************************************************/
        for (int i = 0; i < config.GOODBEAST; i++) {
            container.insert(new GoodBeast(getNextID(), getRandomPos()));
        }

/************************************************BADPLANTS************************************************/
        for (int i = 0; i < config.BADPLANT; i++) {
            container.insert(new BadPlant(getNextID(), getRandomPos()));
        }

/************************************************GOODPLANTS************************************************/
        for (int i = 0; i < config.GOODPLANT; i++) {
            container.insert(new GoodPlant(getNextID(), getRandomPos()));
        }


    }


    /**
     * Generates a new ID.
     * Every ID is unique so every Object has its own
     *
     * @return The ID as Int
     */
    public int getNextID() {
        return ++ID;
    }


    /**
     * Generates a random Position on the Field.
     * If the generated Position is already occupied a new Position is generated
     *
     * @return The random Position as XY Object
     */
    public XY getRandomPos() {
        java.util.Random r = new java.util.Random();
        Entity[][] entities = flatten().getEntities();

        int xCord = r.nextInt(entities[0].length - 1);
        int yCord = r.nextInt(entities.length - 1);

        if (entities[yCord][xCord] != null) return getRandomPos();
        else {
            return new XY(xCord, yCord);
        }
    }


    /**
     * Generates the 2D Field, its a other form of an update because the old one isn't used after that anymore
     *
     * @return The new Field
     */
    public FlattenedBoard flatten() {
        return new FlattenedBoard(this);
    }


    /**
     * Start the next Round.
     * All Entity movements are executed
     */
    public void update() {
        FlattenedBoard FB = flatten();
        container.startNextStep(FB);
    }


    /**
     * Get the Size of the Field
     *
     * @return The Size as XY Object, but in this Case they aren't Coordinates but Length and Width.
     */
    public XY getSize() {
        return size;
    }


    /**
     * Get the actual Container and its Entities
     *
     * @return The Entity Array.
     */
    public EntityContainer getContainer() {
        return container;
    }


    /**
     * Get the Information if a Position is empty
     *
     * @param pos The Position which has to be checked as XY Object
     * @return A Boolean true or false
     */
    public boolean isPositionEmpty(XY pos) {
        return (flatten().getEntity(pos) == null);
    }
}
