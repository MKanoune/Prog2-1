package de.hsa.games.fatsquirrel.Core.Movement;

/**
 * Writes XY Objects with the different Directions in a 3x3 field
 */
public enum RandomMoveCommandType {
    Up(new XY(0,-1)),
    Down(new XY(0,1)),
    Left(new XY(-1,0)),
    Right(new XY(1,0)),
    UpperLeft(new XY(-1,-1)),
    UpperRight(new XY(1,-1)),
    BottomLeft(new XY(-1,1)),
    BottomRight(new XY(1,1));

    public final XY xy;

    RandomMoveCommandType(XY xy) {
        this.xy = xy;
    }
}
