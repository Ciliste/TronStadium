package shesh.tron.server.auth.impl;

import shesh.tron.server.auth.AuthManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class AbstractAuthManager extends UnicastRemoteObject implements AuthManager {

    public AbstractAuthManager() throws RemoteException {

        super();
    }

    public abstract void init();
}
