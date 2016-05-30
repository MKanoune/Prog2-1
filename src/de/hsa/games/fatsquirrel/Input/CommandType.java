package de.hsa.games.fatsquirrel.Input;

/**
 * All Commandos and their Params are saved here
 */
public enum CommandType implements CommandTypeInfo{
    HELP("help", "  * list all commands"),
    EXIT("exit", "  * exit program"),
    MASTER_ENERGY("energy", " * displays the Energy of your Squirrel"),
    SPAWN_MINI("spawn", "<param1> * spawns Mini Squirrel", int.class),
    UP("up", " * move up"),
    DOWN("down", " * move down"),
    LEFT("left", " * move left"),
    RIGHT("right", " * move right");

    final String command;
    final String help;
    final Class<?>[] params;

    CommandType(String command, String help, Class<?>... params) {
        this.command = command;
        this.help = help;
        this.params = params;
    }


    /**
     * Get the Name of the current Command
     * @return The Name String
     */
    @Override
    public String getName() {
        return command;
    }


    /**
     * Get the Help Text for a Command, for the Users who don't know the usage of one Command. Or all
     * @return The Help String
     */
    @Override
    public String getHelpText() {
        return help;
    }


    /**
     * Get the needed Param Types
     * @return All Param Types that the Command need as Class Array
     */
    @Override
    public Class<?>[] getParamTypes() {
        return params;
    }
}
