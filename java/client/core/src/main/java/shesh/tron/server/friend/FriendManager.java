package shesh.tron.server.friend;

import shesh.tron.server.auth.token.EncryptedToken;
import shesh.tron.server.auth.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FriendManager extends Remote {

    public abstract boolean removeFriend(EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException;

    public abstract boolean acceptFriendRequest(String sessionId, EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException;

    public abstract boolean declineFriendRequest(String sessionId, EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException;

    public abstract boolean sendFriendRequest(String sessionId, EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException;

    public abstract User[] getFriends(EncryptedToken token) throws RemoteException;

    public abstract User[] getFriendRequests(String sessionId, EncryptedToken token) throws RemoteException;
}
