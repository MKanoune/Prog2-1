package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;

/**
 * Created by max on 23.05.16 09:20.
 */
interface BotControllerFactoryInterface {
    BotController createMasterBotController(MasterSquirrelBot masterBot);

    BotController createMiniBotController(MiniSquirrelBot miniBot);
}
