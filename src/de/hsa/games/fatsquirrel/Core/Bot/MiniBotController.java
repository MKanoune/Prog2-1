package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Wall;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Movement.XY;
import de.hsa.games.fatsquirrel.Logger.LogProxy;

/**
 * Created by max on 23.05.16 09:26.
 */
public class MiniBotController implements BotController{

    MiniBotController() {
    }

    @Override
    public void nextStep(ControllerContext view) {
        view = (ControllerContext) LogProxy.newInstance(view);
        view.move(XY.RandomMoveCommand());
        Entity next = null;
        for (int y = view.getViewUpperRight().getY(); y < view.getViewLowerLeft().getY(); y++) {
            for (int x = view.getViewLowerLeft().getX(); x < view.getViewUpperRight().getX(); x++) {
                Entity victim = view.getEntity(new XY(x, y));
                if (victim != null && !(victim instanceof Wall) && !(victim instanceof MasterSquirrelBot)) {
                    if (next != null) {

                    } else {
                        next = victim;
                    }
                }

            }
        }
    }
}
