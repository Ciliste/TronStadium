package shesh.tron.server;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.auth.impl.AbstractAuthManager;
import shesh.tron.server.crypto.key.KeyPair;
import shesh.tron.server.crypto.key.KeyPairFactory;
import shesh.tron.server.crypto.key.provider.KeyProvider;
import shesh.tron.server.crypto.key.provider.impl.KeyProviderImpl;
import shesh.tron.server.serverhandler.ServerHandler;
import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.LoggerFactory;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TronServerImpl implements TronServer {

    private final ServerHandler serverHandler;

    public TronServerImpl() throws RemoteException {

        super();

        Logger log = LoggerFactory.getLogger();

        log.info("Creating server");

        log.info("Creating server handler");
        serverHandler = new SqlServerHandler();
        log.info("Server handler created");

        log.info("Server created");
    }

    @Override
    public void start() {

        Logger log = LoggerFactory.getLogger();

        try {

            log.info("Starting server");

            log.info("Initializing server handler");
            serverHandler.init();

            LocateRegistry.createRegistry(1099);

            log.info("Binding AuthManager");
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/authManager", serverHandler.getAuthManager());

            log.info("Binding KeyProvider");
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/keyProvider", serverHandler.getKeyProvider());

            log.info("Binding FriendManager");
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/friendManager", serverHandler.getFriendManager());

            log.info("Binding ServerInfo");
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/serverInfo", serverHandler.getServerInfo());

            log.info("Binding GameManager");
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/gameManager", serverHandler.getGameManager());

            log.info("Server started");
        }
        catch (Exception e) {

            log.fatal("Failed to start server", e);
        }
    }
}
