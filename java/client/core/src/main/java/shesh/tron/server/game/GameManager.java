package shesh.tron.server.game;

import shesh.tron.server.auth.token.EncryptedToken;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote {

    public abstract GameAddress openGame(String sessionId, EncryptedToken token) throws RemoteException;

    public abstract void closeGame(String sessionId, EncryptedToken token, GameAddress lobbyAddress) throws RemoteException;
}
