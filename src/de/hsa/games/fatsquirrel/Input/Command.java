package de.hsa.games.fatsquirrel.Input;

/**
 * Handles one Command and it's Params
 */
public class Command {
    private final Object[] params;
    private final CommandTypeInfo commandType;


    /**
     * Create a Object which is holding the Information of one Command
     * @param commandType Name, Help Text and Param Types as CommandTypeInfo Object
     * @param params Actual Params for the Command as Object Array
     */
    public Command(CommandTypeInfo commandType, Object[] params) {
        this.commandType = commandType;
        this.params = params;
    }


    /**
     * Get the Command Type Info of the Object
     * @return The Command Type, means the Name, Help text and the Param Types that are needed as CommandTypeInfo Object
     */
    public CommandTypeInfo getCommandType() {
        return commandType;
    }


    /**
     * Simply get the Params from the Command, which the Object is holding
     * @return All Params from the Command as Object Array
     */
    public Object[] getParams() {
        return params;
    }
}
