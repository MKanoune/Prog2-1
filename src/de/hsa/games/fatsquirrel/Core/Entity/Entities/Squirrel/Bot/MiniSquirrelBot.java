package de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Bot.BotController;
import de.hsa.games.fatsquirrel.Core.Bot.BotControllerFactory;
import de.hsa.games.fatsquirrel.Core.Bot.ControllerContext;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.*;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Squirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import org.apache.log4j.Logger;

/**
 * Created by max on 23.05.16 09:29.
 */
public class MiniSquirrelBot extends Squirrel {
    private final BotController controller;
    private final MasterSquirrel master;


    /**
     * Create a Entity with the following Params
     *
     * @param ID        The unique ID as Int
     * @param energy    The Energy of the Entity as Int
     * @param direction The Direction to spawn
     */
    public MiniSquirrelBot(int ID, int energy, XY direction, MasterSquirrel master) {
        super(ID, energy, master.getXY().getTempXY(direction));
        this.master = master;

        BotControllerFactory factory = new BotControllerFactory();
        this.controller = factory.createMiniBotController(this);
    }

    @Override
    public void nextStep(EntityContext context) {
        if (getRounds() != 0) {
            updateTimeOut();
            return;
        }
        updateEnergy(-1);

        ControllerContextImplMini view = this.new ControllerContextImplMini(context);
        controller.nextStep(view);
    }

    public MiniSquirrelBot getMiniSquirrelBot() {
        return this;
    }

    public MasterSquirrel getMaster() {
        return master;
    }

    /*************************************
     * InnerClass
     *************************************/
    public class ControllerContextImplMini implements ControllerContext {
        final EntityContext context;
        final XY visibility = new XY(11, 11);
        private final Logger log = Logger.getLogger(ControllerContextImplMini.class.getName());

        ControllerContextImplMini(EntityContext context) {
            this.context = context;
        }

        @Override
        public XY getViewLowerLeft() {
            return context.getViewLowerLeft(MiniSquirrelBot.this, visibility);
        }

        @Override
        public XY getViewUpperRight() {
            return context.getViewLowerLeft(MiniSquirrelBot.this, visibility);
        }

        @Override
        public Entity getEntity(XY pos) {
            if(pos.getX() < getViewLowerLeft().getX() || pos.getX() > getViewUpperRight().getX()) {
                log.error("Out of Range");
                return null;
            }
            else if(pos.getY() > getViewLowerLeft().getY() || pos.getY() < getViewUpperRight().getY()) {
                log.error("Out of Range");
                return null;
            }
            return context.getEntity(pos);
        }

        @Override
        public void move(XY direction) {
            context.tryMove(getMiniSquirrelBot(), direction);
        }

        @Override
        public void spawnMiniBot(XY direction, int energy) {
            //Do Nothing
        }

        @Override
        public int getEnergy() {
            return getMiniSquirrelBot().getEnergy();
        }

        @Override
        public XY getBotPosition() {
            return MiniSquirrelBot.this.getXY();
        }


        /**
         * Dummy
         * @return
         */
        public XY whereisMyMaster() {
            return new XY(0, 0).getDirection(MiniSquirrelBot.this.getXY().getVector(master.getXY()));
        }
    }
}
