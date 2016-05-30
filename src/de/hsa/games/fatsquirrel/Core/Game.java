package de.hsa.games.fatsquirrel.Core;

/**
 * The Game Class.
 * Handles the Game and leads all together
 * The FPS define how fast the Game is (100 means 10fps)
 */
public abstract class Game {
    final State state;
    private static int fps;

    Game(State s) {
        state = s;
    }


    /**
     * Run the Game
     * render(): print on the Screen
     * processInput(): get a Command
     * update(): save all changes to the Board
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        //noinspection InfiniteLoopStatement
        fps = 100;

        while(true) {
            Thread.sleep(fps);
            render();
            processInput();
            update();
        }
    }


    abstract void render();


    abstract void processInput();

    /**
     * Update the Board
     */
    private void update() {
        state.update();
    }
}
