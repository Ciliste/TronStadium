package shesh.tron.screen;

import com.badlogic.gdx.ScreenAdapter;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.navigation.Navigation;
import shesh.tron.server.auth.token.Token;
import shesh.tron.utils.APIUtils;
import shesh.tron.utils.AppDataUtils;
import shesh.tron.utils.endpoint.ApiEndpoint;
import shesh.tron.utils.endpoint.impl.ApiEndpointImpl;
import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.LoggerFactory;

public class FirstScreen extends ScreenAdapter {

    private Navigation navigation;
    private ApiEndpointContext apiEndpointContext;

    public FirstScreen(Navigation navigation, ApiEndpointContext apiEndpointContext) {

        this.navigation = navigation;
        this.apiEndpointContext = apiEndpointContext;
    }

    private boolean requestSent = false;

    @Override
    public void render(float delta) {

        super.render(delta);

        if (!requestSent) {

            requestSent = true;
        }
        else {

            return;
        }

        Logger logger = LoggerFactory.getLogger();

        String token = AppDataUtils.get("token");
        String lastHost = AppDataUtils.get("lastHost");
        if (null != token || null != lastHost) {

            ApiEndpoint apiEndpoint = new ApiEndpointImpl(lastHost);
            apiEndpoint.connect().then(success -> {

                if (success) {

                    apiEndpointContext.setApiEndpoint(apiEndpoint);

                    logger.info("Connected to " + lastHost);

                    apiEndpoint.isValidToken(Token.of(token)).then(valid -> {

                        if (valid) {

                            logger.info("Token is valid, showing menu screen");
                            navigation.showMenuScreen(token);
                        }
                        else {

                            logger.info("Token is not valid, showing login screen");
                            navigation.showLoginScreen();
                        }

                    }).executeAsync();
                }
                else {

                    logger.error("Could not connect to " + lastHost + ", showing login screen");
                }
            }).executeAsync();
        }
        else {

            logger.info("No token found, showing login screen");
            navigation.showLoginScreen();
        }
    }
}
