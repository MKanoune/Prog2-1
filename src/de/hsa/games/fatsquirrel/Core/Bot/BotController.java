package de.hsa.games.fatsquirrel.Core.Bot;

/**
 * Controls the Bot over the NextStep Method
 */
public interface BotController {

    /**
     * start the NextStep of the Bot
     * @param view The View of the Board
     */
    void nextStep(ControllerContext view);
}
