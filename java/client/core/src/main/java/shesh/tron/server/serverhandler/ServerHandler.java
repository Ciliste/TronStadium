package shesh.tron.server.serverhandler;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.crypto.key.provider.KeyProvider;
import shesh.tron.server.friend.FriendManager;
import shesh.tron.server.game.GameManager;
import shesh.tron.server.info.ServerInfo;

public interface ServerHandler {

    public abstract void init();

    public abstract AuthManager getAuthManager();

    public abstract FriendManager getFriendManager();

    public abstract ServerInfo getServerInfo();

    public abstract KeyProvider getKeyProvider();

    public abstract GameManager getGameManager();
}
