package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;

/**
 * Created by max on 23.05.16 09:28.
 */
public class BotControllerFactory implements BotControllerFactoryInterface{
    @Override
    public BotController createMasterBotController(MasterSquirrelBot masterBot) {
        return new MasterBotController();
    }

    @Override
    public BotController createMiniBotController(MiniSquirrelBot miniBot) {
        return new MiniBotController();
    }
}
