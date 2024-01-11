package shesh.tron.server.info;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInfo extends Remote {

    public abstract String getServerName() throws RemoteException;
    public abstract String getServerDescription() throws RemoteException;
}
