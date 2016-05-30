package de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot;

import de.hsa.games.fatsquirrel.Core.Board.EntityContext;
import de.hsa.games.fatsquirrel.Core.Bot.BotController;
import de.hsa.games.fatsquirrel.Core.Bot.BotControllerFactory;
import de.hsa.games.fatsquirrel.Core.Bot.ControllerContext;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityType;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import org.apache.log4j.Logger;

/**
 * Created by max on 23.05.16 10:12.
 */
public class MasterSquirrelBot extends MasterSquirrel{
    private final BotController controller;

    public MasterSquirrelBot(int ID, XY xy) {
        super(ID, xy);
        BotControllerFactory factory = new BotControllerFactory();
        this.controller = factory.createMasterBotController(this);
    }

    private MasterSquirrelBot getMasterSquirrelBot() {
        return this;
    }

    @Override
    public void nextStep(EntityContext context) {
        if (getRounds() != 0) {
            updateTimeOut();
            return;
        }

        ControllerContextImplMaster view = this.new ControllerContextImplMaster(context);
        controller.nextStep(view);
    }

/*************************************InnerClass*************************************/
    public class ControllerContextImplMaster implements ControllerContext {
    final EntityContext context;
    final XY visibility = new XY(21, 21);
    private final Logger log = Logger.getLogger(ControllerContextImplMaster.class.getName());

    ControllerContextImplMaster(EntityContext context) {
        this.context = context;
    }

    @Override
    public XY getViewLowerLeft() {
        return context.getViewLowerLeft(MasterSquirrelBot.this, visibility);
    }

    @Override
    public XY getViewUpperRight() {
        return context.getViewLowerLeft(MasterSquirrelBot.this, visibility);
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
        context.tryMove(getMasterSquirrelBot(), direction);
    }

    @Override
    public void spawnMiniBot(XY direction, int energy) {
        context.spawnChildBot(getMasterSquirrelBot(), energy, direction);
    }

    @Override
    public int getEnergy() {
        return getMasterSquirrelBot().getEnergy();
    }

    @Override
    public XY getBotPosition() {
        return MasterSquirrelBot.this.getXY();
    }
}
}
