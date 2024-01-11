package shesh.tron.server.game.impl;

import shesh.tron.server.game.GameAddress;
import shesh.tron.server.game.GameController;
import shesh.tron.server.game.logic.net.GameServer;
import shesh.tron.server.game.logic.net.impl.KryoGameServer;

/**
 * ThreadedGameController is a GameController that runs the game in a separate thread.
 */
public class ThreadedGameController implements GameController {

    private final GameAddress gameAddress;

    public ThreadedGameController(String gameId, int tcpPort, int udpPort) {

        gameAddress = new GameAddressImpl(tcpPort, udpPort, gameId);
    }

    @Override
    public GameAddress getGameAddress() {

        return gameAddress;
    }

    @Override
    public void start() {

        GameServer gameServer = new KryoGameServer();
        gameServer.bind(gameAddress.getTCPPort(), gameAddress.getUDPPort());

        gameServer.start();
    }

    @Override
    public void stop() {

        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
