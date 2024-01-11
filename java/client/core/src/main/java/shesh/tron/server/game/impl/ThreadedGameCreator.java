package shesh.tron.server.game.impl;

import shesh.tron.server.game.GameController;
import shesh.tron.server.game.GameCreator;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Threaded game creator.
 * Will create a game in a new thread from the program which called the createGame method.
 */
public class ThreadedGameCreator implements GameCreator {

    @Override
    public GameController createGame(String gameId) {

        int tcpPort = findFreePort();
        int udpPort;

        do {

            udpPort = findFreePort();
        }
        while (udpPort == tcpPort);

        return new ThreadedGameController(gameId, tcpPort, udpPort);
    }

    private static int findFreePort() {

        int port;

        do {

            port = (int) (Math.random() * 65535);
        }
        while (!isPortFree(port));

        return port;
    }

    private static boolean isPortFree(int port) {

        try (ServerSocket socket = new ServerSocket(port)) {

            return true;
        }
        catch (IOException e) {

            return false;
        }
    }
}
