package shesh.tron.server.game;

public interface GameAddress {

    public abstract int getTCPPort();

    public abstract int getUDPPort();

    public abstract String getGameId();
}
