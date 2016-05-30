package de.hsa.games.fatsquirrel.Core.Board;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.*;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Squirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

/**
 * The 2D Board and its Entities
 */
public class FlattenedBoard implements EntityContext, BoardView {
    private static Entity[][] entities;
    private final Board board;
    private final XY size;


    /**
     * The Constructor for the 2D Field.
     * Every Entity of the Container is inserted at their Coordinates
     * @param board The Board
     */
    public FlattenedBoard (Board board) {
        this.board = board;
        size = board.getSize();
        entities = new Entity[size.getY()][size.getX()];

        for(int i = 0; i < size.getX()*size.getY(); i++) {
            if(board.getContainer().getEntity(i) == null) {
                //Do Nothing
            }
            else {
                XY pos = board.getContainer().getEntity(i).getXY();
                int x = pos.getX();
                int y = pos.getY();
                entities[y][x] = board.getContainer().getEntity(i);
            }
        }
    }


    /**
     * Get the Size of the Field
     * @return The Size as XY Object, but X and Y are Length and Width
     */
    @Override
    public XY getSize() {
        return size;
    }


    /**
     * Get a Entity at a specific Position
     * @Warning If Position is Empty null is returned. So check isPositionEmpty before.
     * @param pos XY Object with the Coordinates
     * @return The Entity at the Position
     */
    @Override
    public Entity getEntity(XY pos) {
        Entity entity;
        entity = entities[pos.getY()][pos.getX()];
        return entity;
    }


    /**
     * Tries to move the MiniSquirrel
     * tryMoveSquirrel: Calls the Method for all Entities that are same to the MasterSquirrel
     * MasterSquirrel: If the MiniSquirrel is a slave of this MasterSquirrel the Entity is killed and the Energy will be added. Else the Entity is killed
     * @param miniSquirrel The MiniSquirrel Entity
     * @param moveDirection The move direction to the wanted Position as XY Object
     */
    @Override
    public void tryMove(MiniSquirrelBot miniSquirrel, XY moveDirection) {
        XY tmpPos = miniSquirrel.getXY().getTempXY(moveDirection);
        Entity tmpEntity = getEntity(tmpPos);

/************************************************All Other Entities************************************************/
        tryMoveSquirrel(miniSquirrel, tmpPos, tmpEntity, moveDirection);


/************************************************MasterSquirrel************************************************/
        if(getEntityType(tmpPos) == EntityType.MASTERSQUIRREL) {
            MasterSquirrel masterSquirrel = (MasterSquirrel)tmpEntity;
            if(masterSquirrel.checkSlave(miniSquirrel)) {
                masterSquirrel.updateEnergy(miniSquirrel.getEnergy());
                kill(miniSquirrel);
            }
            else {
                kill(miniSquirrel);
            }
        }
    }


    /**
     * Tries to move the MasterSquirrel
     * tryMoveSquirrel: Calls the Method for all Entities that are same to the MiniSquirrel
     * MiniSquirrel: If the MiniSquirrel is a slave of this MasterSquirrel the Entity is killed and the Energy will be added. The MasterSquirrel will move to the Position
     *               Else the Entity is killed
     * @param masterBot The MasterSquirrel which is to move
     * @param moveDirection The move Vector as XY Object
     */
    @Override
    public void tryMove(MasterSquirrel masterBot, XY moveDirection) {
        XY tmpPos = masterBot.getXY().getTempXY(moveDirection);
        Entity tmpEntity = getEntity(tmpPos);

        tryMoveSquirrel(masterBot, tmpPos, tmpEntity, moveDirection);

/************************************************MiniSquirrel************************************************/
        if(getEntityType(tmpPos) == EntityType.MINISQUIRREL) {
            if(masterBot.checkSlave((MiniSquirrelBot) tmpEntity)) {
                masterBot.updateEnergy(tmpEntity.getEnergy());
                kill(tmpEntity);
                masterBot.getXY().moveDirection(moveDirection);
            }
            else {
                kill(tmpEntity);
            }
        }
    }


    /**
     * Wall: don't move and deducts energy
     * BadBeast: deducts energy, updates the Bites from the BadBeast and if the Bites are 0 it will be killed and replaced. The MasterSquirrel will move to the Position
     * GoodPlant: Kills and replace the Entity and updates the Energy to the MasterSquirrel. The MasterSquirrel will move to the Position
     * BadPlant: Kills and replace the Entity and deducts the energy. The MasterSquirrel will move to the Position
     * GoodPlant: Kills and replace the Entity, the Energy is updated. The MasterSquirrel will move to the Position
     * NoEntity: The MasterSquirrel will move to the Position
     * @param squirrel The Squirrel Entity, Master or Mini
     * @param tmpPos The temporary Position
     * @param tmpEntity The temporary Entity
     * @param moveDirection The wanted move direction as XY Object
     */
    private void tryMoveSquirrel(Entity squirrel, XY tmpPos, Entity tmpEntity, XY moveDirection) {
/************************************************Wall************************************************/
        if(getEntityType(tmpPos) == EntityType.WALL) {
            squirrel.updateEnergy(tmpEntity.getEnergy());
            squirrel.setTimeOut(3);
            return;
        }

/************************************************BadBeast************************************************/
        if(getEntityType(tmpPos) == EntityType.BADBEAST) {
            BadBeast badBeast = (BadBeast)tmpEntity;
            badBeast.updateBites();
            if(badBeast.getBites() == 0) killAndReplace(badBeast);
            squirrel.updateEnergy(tmpEntity.getEnergy());
            return;
        }

/************************************************GoodBeast************************************************/
        if(getEntityType(tmpPos) == EntityType.GOODBEAST) {
            squirrel.updateEnergy(tmpEntity.getEnergy());
            killAndReplace(tmpEntity);
            squirrel.getXY().moveDirection(moveDirection);
            return;
        }

/************************************************BadPlant************************************************/
        if(getEntityType(tmpPos) == EntityType.BADPLANT) {
            squirrel.updateEnergy(tmpEntity.getEnergy());
            killAndReplace(tmpEntity);
            squirrel.getXY().moveDirection(moveDirection);
            return;
        }

/************************************************GoodPlant************************************************/
        if(getEntityType(tmpPos) == EntityType.GOODPLANT) {
            squirrel.updateEnergy(tmpEntity.getEnergy());
            killAndReplace(tmpEntity);
            squirrel.getXY().moveDirection(moveDirection);
            return;
        }

/************************************************NoEntity************************************************/
        if(getEntityType(tmpPos) == EntityType.NOENTITY) {
            squirrel.getXY().moveDirection(moveDirection);
        }
    }


    /**
     * Tries to move the GoodBeast
     * If the wanted Position is empty the GoodBeast moves else another command is get.
     * @param goodBeast The GoodBeast which is to move
     * @param moveDirection The move Vector as XY Object
     */
    @Override
    public void tryMove(GoodBeast goodBeast, XY moveDirection) {
        if(isValidPosition(goodBeast.getXY().getTempXY(moveDirection))) {
            goodBeast.getXY().moveDirection(moveDirection);
        }
        else {
            goodBeast.setTimeOut(0);
            goodBeast.nextStep(this);
        }
    }


    /**
     * Tries to move the BadBeast
     * At first a Temporary XY Object is created, on this Position the Entity is get.
     * If the Entity is a MasterSquirrel the BadBeast bites the MasterSquirrel.
     * The Energy and the Bites are updated.
     * Else its checked if the Position is empty.
     * @param badBeast The BadBeast which is to move
     * @param moveDirection The move Vector as XY Object
     */
    @Override
    public void tryMove(BadBeast badBeast, XY moveDirection) {
        XY tmpPos = badBeast.getXY().getTempXY(moveDirection);

        Entity tmpEntity = getEntity(tmpPos);

        if(getEntityType(tmpPos) == EntityType.MASTERSQUIRREL) {
            tmpEntity.updateEnergy(badBeast.getEnergy());
            badBeast.updateBites();
            if(badBeast.getBites() == 0) killAndReplace(badBeast);
        }
        else if(tmpEntity == null && isValidPosition(tmpPos)) {
            badBeast.getXY().moveDirection(moveDirection);
        }
        else {
            badBeast.setTimeOut(0);
            badBeast.nextStep(this);
        }
    }


    /**
     * Kills a Entity
     * @param entity The Entity which is to be killed
     */
    @Override
    public void kill(Entity entity) {
        board.getContainer().delete(entity);
    }


    /**
     * Kills a Entity and replace it at a random Position.
     * @param entity The Entity which is to delete
     */
    @Override
    public void killAndReplace(Entity entity) {
        EntityType type = getEntityType(entity.getXY());
        board.getContainer().delete(entity);

        switch(type) {
            case WALL:
                board.getContainer().insert(new Wall(board.getNextID(), board.getRandomPos()));
                return;
            case BADBEAST:
                board.getContainer().insert(new BadBeast((board.getNextID()), board.getRandomPos()));
                return;
            case BADPLANT:
                board.getContainer().insert(new BadPlant((board.getNextID()), board.getRandomPos()));
                return;
            case GOODBEAST:
                board.getContainer().insert(new GoodBeast((board.getNextID()), board.getRandomPos()));
                return;
            case GOODPLANT:
                board.getContainer().insert(new GoodPlant((board.getNextID()), board.getRandomPos()));
                return;
            default:
                System.out.print("Error on killAndReplace");
        }
    }


    /**
     * Gets the Entity Type at a specific Location
     * The Entity Type is a custom Type, defined in the Enum EntityType
     * @param pos The Position of the Entity, which EntityType you want
     * @return The Entity at the Position
     */
    @Override
    public EntityType getEntityType(XY pos) {
        Entity collision = entities[pos.getY()][pos.getX()];

        if (collision instanceof Wall) return EntityType.WALL;
        if (collision instanceof BadBeast) return EntityType.BADBEAST;
        if (collision instanceof BadPlant) return EntityType.BADPLANT;
        if (collision instanceof GoodBeast) return EntityType.GOODBEAST;
        if (collision instanceof GoodPlant) return EntityType.GOODPLANT;
        if (collision instanceof MasterSquirrel) return EntityType.MASTERSQUIRREL;
        if (collision instanceof MiniSquirrelBot) return EntityType.MINISQUIRREL;
        return EntityType.NOENTITY;
    }


    /**
     * Get the nearest Player to a specific Position.
     * The sear Radius is a 7x7 field.
     * For a easier Code the Center is set to the Top Right Corner
     * If after that a Position is out of Range it's set to 0
     * After that the whole Area is checked if a Maser or Mini Squirrel is in the field
     * The first one found and the next is found, it will check the moves to each of them.
     * If entity1 needs less moves than entity2, entity2 is overridden with entity1. Else its done otherwise
     * @param pos The Position where all Entities are searched as XY Object
     * @return The Entity of the nearest Player, if no Player found null
     */
    @Override
    public Entity getNearestPlayerEntity(XY pos) {
        Entity entity1;
        Entity entity2 = null;
        XY tmp;

        int x = pos.getX() - 6;
        if (x < 0) x = 0;

        int y = pos.getY() - 6;
        if (y < 0) y = 0;

        int xMax = x+13;
        if(xMax >= entities[0].length) xMax = entities[0].length-1;

        int yMax = x+13;
        if(yMax >= entities.length) yMax = entities.length-1;

        for (int i = x; i < xMax; i++) {
            for (int j = y; j < yMax; j++) {
                tmp = new XY(i, j);
                if (getEntity(tmp) instanceof MasterSquirrel || getEntity(tmp) instanceof MiniSquirrelBot) {
                    entity1 = entities[j][i];
                    if(entity2 != null && entity2 != entity1) {
                        if(getMoves(pos.getVector(entity1.getXY())) <= getMoves(pos.getVector(entity2.getXY()))) {
                            entity2 = entity1;
                        }
                        else entity1 = entity2;
                    }
                    else entity2 = entity1;
                }
            }
        }
        return entity2;
    }


    @Override
    public void spawnChildBot(MasterSquirrel parent, int energy, XY direction) {
        Entity mini = parent.createSlave(board.getNextID(), energy, direction);
        board.getContainer().insert(mini);
    }

    @Override
    public void allahuAkbar(Entity[][] view, MiniSquirrelBot miniSquirrelBot){
        System.out.println("Allahu Akbar");
        int impactRadius = 5;
        int impactArea = (int) (impactRadius * impactRadius * Math.PI);

        for(int x = 0; x < view[0].length; x++){
            for (Entity[] aView : view) {
                Entity e = aView[x];
                if (e != null && !(e instanceof Wall) && e != (miniSquirrelBot.getMaster()) && e != miniSquirrelBot.getMiniSquirrelBot()) {
                    int distance = 8; //(int)XY.vectorValue2(e.xy,xy);
                    int energyloss = (-200) * (miniSquirrelBot.getEnergy() / impactArea) * (1 - distance / impactRadius);
                    if (energyloss <= 0) {
                        energyloss *= -1;
                    }
                    if (e instanceof BadBeast || e instanceof BadPlant) {
                        if (e.getEnergy() + energyloss >= 0) {
                            miniSquirrelBot.getMaster().updateEnergy(e.getEnergy() * -1);
                            killAndReplace(e);
                        } else {
                            e.updateEnergy(energyloss);
                        }
                    } else if (e instanceof GoodBeast || e instanceof GoodPlant) {
                        if (e.getEnergy() - energyloss <= 0) {
                            miniSquirrelBot.getMaster().updateEnergy(e.getEnergy());
                            killAndReplace(e);
                        } else {
                            e.updateEnergy(energyloss * -1);
                        }
                    }
                }
            }
        }
        kill(miniSquirrelBot.getMiniSquirrelBot());
    }

    @Override
    public XY getViewLowerLeft(Squirrel squirrel, XY visibility) {
        XY position = squirrel.getXY();
        int x = position.getX() - ((visibility.getX() - 1) / 2);
        if (x < 0) {
            x = 0;
        }
        int y = position.getY() + ((visibility.getY() - 1) / 2);
        if (y > getSize().getY()) {
            y = getSize().getY();
        }

        return new XY(x, y);
    }

    @Override
    public XY getViewUpperRight(Squirrel squirrel, XY visibility) {
        XY position = squirrel.getXY();
        int x = position.getX() + ((visibility.getX() - 1) / 2);
        if (x > getSize().getX()) {
            x = getSize().getX();
        }
        int y = position.getY() - ((visibility.getY() - 1) / 2);
        if (y < 0) {
            y = 0;
        }

        return new XY(x, y);
    }


    /**
     * Calculates the moves that are needed to execute the complete Vector
     * @param vector The Vector that is needed to be calculated as XY Object
     * @return The moves that are needed as Int
     */
    private int getMoves(XY vector) {
        int moves = 0;

        while (vector.getX() != 0 & vector.getY() != 0) {
            if (vector.getX() > 0) vector.updateX(-1);
            else if (vector.getX() < 0) vector.updateX(1);

            if (vector.getY() > 0) vector.updateY(-1);
            else if (vector.getY() < 0) vector.updateY(1);
            moves++;
        }
        return moves;
    }


    /**
     *Get the actual Board
     * @return The Board Object
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Checks if a Position is empty and in the Filed or not
     * @param destination The Position which has to be checked
     * @return Boolean true or false if Valid or not
     */
    private boolean isValidPosition(XY destination) {
        return !(destination.getY() < 0 || destination.getX() < 0 || destination.getY() >= entities.length || destination.getX() >= entities[0].length) && board.isPositionEmpty(destination);
    }


    /**
     * Get the Information if a Position is empty
     * @param position The Position which has to be checked as XY Object
     * @return A Boolean true or false
     */
    public boolean isPositionEmpty(XY position) {
        return entities[position.getY()][position.getX()] == null;
    }


    /**
     * Get the current 2D Boards Entities
     * @return The 2D Entity Array (y/x)
     */
    public Entity[][] getEntities() {
        return entities;
    }
}
