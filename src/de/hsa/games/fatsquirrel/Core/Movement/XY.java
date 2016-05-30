package de.hsa.games.fatsquirrel.Core.Movement;

/**
 * Manage one Items Coordinates, which contain X and Y.
 * All possible changes are handled here.
 */
public class XY {
    private int xCord;
    private int yCord;
    static java.util.Random r = new java.util.Random();

    public XY(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public XY getXY() {
        return this;
    }

    public int getX() {
        return xCord;
    }

    public int getY() {
        return yCord;
    }


    /**
     *Update X and Y at the Same time, with an given XY Object
     */
    public void moveDirection(XY XYUpdate) {
        xCord += XYUpdate.getX();
        yCord += XYUpdate.getY();
    }


    /**
     * Returns the Vector to the Destination
     */
    public XY getVector(XY destination) {
        int x, y;

        x = destination.getX() - xCord;
        y = destination.getY() - yCord;

        return new XY(x, y);
    }


    /**
     * Updates the Y Cord with the given Value
     */
    public void updateY(int y) {
        yCord += y;
        if (yCord < 0) yCord = 0;
    }

    /**
     * Update the X Cord with the given Value
     */
    public void updateX(int x) {
        xCord += x;
        if (xCord < 0) xCord = 0;
    }


    /**
     * Get a XY Object which contains a temporary Object from the Destination,
     * so it's possible to change things, to check if the Position is empty or something.
     * This make's it unnecessary to set the edited Object back.
     * @param destinationVector The Vector from the Object to the Destination as XY Object
     * @return A XY Object from the Position where this Object want to go
     */
    public XY getTempXY(XY destinationVector) {
        if (destinationVector == null) return this;
        int x = xCord + destinationVector.getX();
        int y = yCord + destinationVector.getY();
        return new XY(x, y);
    }


    /**
     * Set the xCord to a specific value
     * @param xCord The xCord as int
     */
    public void setX(int xCord) {
        this.xCord = xCord;
    }


    /**
     * Set the yCord to a specific value
     * @param yCord The yCord as int
     */
    public void setY(int yCord) {
        this.yCord = yCord;
    }


    public XY getDirection(XY vector) {
        int directionX, directionY;

        if (vector.getX() > 0) directionX = 1;
        else if (vector.getX() < 0) directionX = -1;
        else directionX = 1;

        if (vector.getY() > 0) directionY = 1;
        else if (vector.getY() < 0) directionY = -1;
        else directionY = 1;

        return new XY(directionX, directionY);
    }

    public static XY RandomMoveCommand(){
        int random = r.nextInt(7);
        XY move = null;

        switch(random){
            case 0: move = RandomMoveCommandType.Up.xy; break;
            case 1: move = RandomMoveCommandType.Down.xy; break;
            case 2: move = RandomMoveCommandType.Left.xy; break;
            case 3: move = RandomMoveCommandType.Right.xy; break;
            case 4: move = RandomMoveCommandType.UpperLeft.xy; break;
            case 5: move = RandomMoveCommandType.UpperRight.xy; break;
            case 6: move = RandomMoveCommandType.BottomLeft.xy; break;
            case 7: move = RandomMoveCommandType.BottomRight.xy; break;
        }
        return move;
    }
}
