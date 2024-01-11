package shesh.tron.server.game;

/**
 * Link between the game and the program that created it.
 */
public interface GameController {

    public abstract GameAddress getGameAddress();

    public abstract void start();

    public abstract void stop();
}
