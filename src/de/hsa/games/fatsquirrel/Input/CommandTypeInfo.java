package de.hsa.games.fatsquirrel.Input;

/**
 * Managed the Info of the Enumeration CommandType
 */
public interface CommandTypeInfo {
    String getName();

    String getHelpText();

    Class<?>[] getParamTypes();
}
