package de.hsa.games.fatsquirrel.Input;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Scans the Console for a Command, only in use if ConsoleUI is activated
 */
public class CommandScanner {
    private final CommandTypeInfo[] commandTypeInfo;
    private final BufferedReader inputReader;


    /**
     * Scan the next Command, only a right command is accepted.
     * At first it tries to read the Line.
     * If this was correct the String is spliced into its parts without the space.
     * The First Part is always the Command. This Command is searched in the CommandTypeInfo.
     * If a accordance is found it will check if Params are needed.
     * If not the command is returned
     * If there are Params the next Part is considered
     * Because at the 19.05.2016 we only have one Command with a Param Int we only check for the int Param.
     * If it's not a Int the return is null
     * @return The Command or null if wrong input
     */
    public Command next() {
        String input;
        String[] inputSplit;
        Command command;
        Object[] params;

        try {
            input = inputReader.readLine();
            inputSplit = input.split(" ");

            for (CommandTypeInfo aCommandTypeInfo : commandTypeInfo) {
                if (aCommandTypeInfo.getName().equals(inputSplit[0])) {
                    Class<?>[] paramTypes = aCommandTypeInfo.getParamTypes();
                    if (paramTypes == null) {
                        command = new Command(aCommandTypeInfo, null);
                    } else {
                        params = new Object[paramTypes.length];
                        for (int j = 0; j < paramTypes.length; j++) {
                            params[j] = inputSplit[j + 1];
                            if (paramTypes[j] == int.class) {
                                params[j] = (Integer.parseInt((String) params[j]));
                            } else {
                                return null;
                            }
                            //Add more ifs here when you have more values
                        }
                        command = new Command(aCommandTypeInfo, params);
                    }
                    return command;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("Error in Command Scanner");
        }

        return null;
    }


    /**
     * Constructor for the Scanner
     * @param commandTypeInfo The Information about all possible Commands as CommandTypeInfo
     * @param inputReader The reader for the Input as BufferedReader
     */
    public CommandScanner(CommandTypeInfo[] commandTypeInfo, BufferedReader inputReader) {
        this.commandTypeInfo = commandTypeInfo;
        this.inputReader = inputReader;
    }
}
