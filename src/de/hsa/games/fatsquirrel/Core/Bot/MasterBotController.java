package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import de.hsa.games.fatsquirrel.Logger.LogProxy;

/**
 * Created by max on 23.05.16 09:26.
 */
public class MasterBotController implements BotController{

    MasterBotController() {
    }


    @Override
    public void nextStep(ControllerContext view) {
        view = (ControllerContext) LogProxy.newInstance(view);
        view.move(new XY(1,1));
    }
}
