package de.hsa.games.fatsquirrel.Core.Bot;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.Bot.MiniSquirrelBot;

/**
 * Crates new Bots
 */
interface BotControllerFactoryInterface {
    /**
     * Create a MasterBotController to control the bot
     * @param masterBot The MasterBot we want to create
     * @return The BotController Object
     */
    BotController createMasterBotController(MasterSquirrelBot masterBot);

    /**
     * Create a MiniBotController to control the Bot
     * @param miniBot The MiniBot we want to controll
     * @return The BotController Object
     */
    BotController createMiniBotController(MiniSquirrelBot miniBot);
}
