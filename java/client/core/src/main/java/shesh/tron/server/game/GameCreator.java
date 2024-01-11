package shesh.tron.server.game;

/**
 * Strategy pattern for creating games.
 */
public interface GameCreator {

    /**
     * Creates a game.
     * @see GameAddress
     */
    public abstract GameController createGame(String gameId);
}
