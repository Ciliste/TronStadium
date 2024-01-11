package shesh.tron.screen.game_lobby;

import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.context.TokenContext;
import shesh.tron.screen.main.MainMenuNavigation;
import shesh.tron.screen.panel.AbstractPanel;
import shesh.tron.server.auth.token.Token;
import shesh.tron.server.game.logic.net.GameClient;
import shesh.tron.server.game.logic.net.impl.KryoGameClient;
import shesh.tron.utils.logger.LoggerFactory;

public class PlayPanel extends AbstractPanel {

    private final MainMenuNavigation navigation;
    private final ApiEndpointContext apiEndpointContext;

    public PlayPanel(MainMenuNavigation navigation, ApiEndpointContext apiEndpointContext, TokenContext tokenContext) {

        super();
        this.navigation = navigation;
        this.apiEndpointContext = apiEndpointContext;

        apiEndpointContext.getApiEndpoint().openGame(Token.of(tokenContext.getToken()))
            .then(gameAddress -> {

                LoggerFactory.getLogger().info("Game address: " + gameAddress);

                GameClient gameClient = new KryoGameClient();
                gameClient.bind(gameAddress, apiEndpointContext.getApiEndpoint());
                gameClient.start();

            })
            .error(e -> {

                LoggerFactory.getLogger().error("Error opening game", e);

            })
            .executeAsync();
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initUIListeners() {

    }
}
