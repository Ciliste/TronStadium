package shesh.tron.server.game.logic.net.impl;

import com.esotericsoftware.kryonet.Client;
import shesh.tron.server.game.GameAddress;
import shesh.tron.server.game.logic.net.GameClient;
import shesh.tron.utils.endpoint.ApiEndpoint;
import shesh.tron.utils.logger.LoggerFactory;
import shesh.tron.worker.request.Request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KryoGameClient implements GameClient {

    private Map<Integer, Request<?>> requestMap;

    private GameAddress gameAddress;
    private ApiEndpoint apiEndpoint;

    private Client client;

    @Override
    public void bind(GameAddress gameAddress, ApiEndpoint apiEndpoint) {

        this.gameAddress = gameAddress;
        this.apiEndpoint = apiEndpoint;
    }

    @Override
    public void start() {

        try {

            requestMap = new ConcurrentHashMap<>();

            client = new Client();
            client.start();

            client.connect(5000, apiEndpoint.getHost(), gameAddress.getTCPPort(), gameAddress.getUDPPort());
        }
        catch (Exception e) {

            LoggerFactory.getLogger().error("Failed to start KryoGameClient.", e);
        }
    }

    @Override
    public void stop() {


    }

    @Override
    public void sendRequest(Request<?> request) {

        requestMap.put(requestMap.size(), request);


    }
}
