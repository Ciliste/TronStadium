package shesh.tron.server.game.logic.net;

public interface GameServer {

    public abstract void bind(int tcpPort, int udpPort);

    public abstract void start();

    public abstract void stop();
}
