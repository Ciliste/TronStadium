package shesh.tron.server.game.logic.net.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import shesh.tron.server.game.logic.net.GameServer;
import shesh.tron.utils.logger.LoggerFactory;

public class KryoGameServer implements GameServer {

    private int tcpPort;
    private int udpPort;

    private Server server;

    @Override
    public void bind(int tcpPort, int udpPort) {

        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    @Override
    public void start() {

        try {

            server = new Server();
            server.bind(tcpPort, udpPort);
            server.start();
        }
        catch (Exception e) {

            LoggerFactory.getLogger().error("Failed to start KryoGameServer.", e);
        }
    }

    @Override
    public void stop() {

        server.stop();
    }
}
