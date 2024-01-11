package shesh.tron.server.game.impl;

import shesh.tron.server.game.GameAddress;

import java.io.Serializable;

public class GameAddressImpl implements GameAddress, Serializable {

    private int tcpPort;
    private int udpPort;
    private String gameId;

    public GameAddressImpl(int tcpPort, int udpPort, String gameId) {

        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.gameId = gameId;
    }

    @Override
    public int getTCPPort() {

        return tcpPort;
    }

    @Override
    public int getUDPPort() {

        return udpPort;
    }

    @Override
    public String getGameId() {

        return gameId;
    }

    @Override
    public String toString() {

        return "GameAddressImpl{" +
                "tcpPort=" + tcpPort +
                ", udpPort=" + udpPort +
                ", gameId='" + gameId + '\'' +
                '}';
    }
}
