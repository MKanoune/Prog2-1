package de.hsa.games.fatsquirrel.Core.Entity;

/**
 * Created by max on 17.05.16 09:22.
 */
public enum EntityType {
    WALL("X"),
    BADBEAST("B"),
    BADPLANT("T"),
    GOODBEAST("G"),
    GOODPLANT("t"),
    MASTERSQUIRREL("M"),
    MINISQUIRREL("m"),
    NOENTITY(" ");


    public final String name;

    EntityType(String name) {
        this.name = name;
    }
}
