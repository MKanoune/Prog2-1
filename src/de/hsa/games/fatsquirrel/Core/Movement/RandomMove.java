package de.hsa.games.fatsquirrel.Core.Movement;

/**
 * Manages the Random moves in an 3x3 field.
 * Every Move has it's own Random Move Object, that makes it Possible to switch between every Possibility.
 * If one is impossible the Possibility get deleted and another one is taken, till there are no more.
 */
public class RandomMove {
    //There are 8 possible moves around the Entity (Field of 3x3), the 8 in the Array is for managing
    //how many Possibilities are left
    private final int[] possibilities = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final java.util.Random r = new java.util.Random();


    /**
     * Constructor is empty because all what is needed is already available
     */
    public RandomMove(){}


    /**
     * Get the current length of the Arrays Possibilities, if one Possibility is 8 means that there is no Possibility
     * and all before are the Possibilities
     * @return The left Possibilities as Int
     */
    private int getI() {
        int i = 0;
        while (possibilities[i] != 8) {
            i++;
        }
        return i-1;
    }


    /**
     * Get one Random move Command as an XY Object.
     * At first with getI() it checks if there are Possibilities
     * If there is only one Possibility left it takes it
     * Else a random Value is taken
     * The switch only decides based on the Value, which Vector is taken
     * After that the just taken Possibility gets deleted
     * @return One of the 8 possible Vectors as XY Object
     */
    public XY getRandomMoveCommand() {
        int i = getI();

        if(possibilities[0] == 8) {
            System.out.println("No Possibilities in RandomMove");
            return null;
        }

        int nextCommand;

        if(i == 0) {
            nextCommand = 0;
        }
        else {
            nextCommand = r.nextInt(i);
        }

        XY moveCommand;

        switch (possibilities[nextCommand]) {
            case 0: moveCommand = RandomMoveCommandType.Up.xy;
                break;
            case 1: moveCommand = RandomMoveCommandType.Down.xy;
                break;
            case 2: moveCommand = RandomMoveCommandType.Left.xy;
                break;
            case 3: moveCommand = RandomMoveCommandType.Right.xy;
                break;
            case 4: moveCommand = RandomMoveCommandType.UpperLeft.xy;
                break;
            case 5: moveCommand = RandomMoveCommandType.UpperRight.xy;
                break;
            case 6: moveCommand = RandomMoveCommandType.BottomLeft.xy;
                break;
            case 7: moveCommand = RandomMoveCommandType.BottomRight.xy;
                break;
            default: moveCommand = null;
        }
        delete(possibilities[nextCommand]);

        return moveCommand;
    }


    /**
     * Delete one Possibility and lets the others move up, so it's unnecessary to go through the whole Array
     * @param possibility The Possibility that is wanted to be deleted as Int
     */
    private void delete(int possibility) {
        for(int i = 0; i < possibilities.length; i++) {
            if(possibilities[i] == possibility) {
                System.arraycopy(possibilities, i + 1, possibilities, i, possibilities.length - 1 - i);
                return;
            }
        }
    }
}
