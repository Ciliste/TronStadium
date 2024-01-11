package shesh.tron.server.game.logic.net;

import shesh.tron.server.game.GameAddress;
import shesh.tron.utils.endpoint.ApiEndpoint;
import shesh.tron.worker.request.Request;

public interface GameClient {

    public abstract void bind(GameAddress gameAddress, ApiEndpoint apiEndpoint);

    public abstract void start();

    public abstract void stop();

    public abstract void sendRequest(Request<?> request);
}
