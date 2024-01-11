package shesh.tron.screen.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.panel.AbstractPanel;
import shesh.tron.server.auth.token.Token;
import shesh.tron.utils.APIUtils;

public class SettingsPanel extends AbstractPanel {

    private VisLabel infoLabel;
    private VisTextButton backButton;

    private MainMenuNavigation navigation;

    public SettingsPanel(String token, MainMenuNavigation navigation, ApiEndpointContext apiEndpointContext) {

        super();

        this.navigation = navigation;

        apiEndpointContext.getApiEndpoint().getUser(Token.of(token)).then(user -> {

            infoLabel.setText("Welcome " + user.getUsername());

        }).error(error -> {

            infoLabel.setText("Error: " + error.getMessage());

        }).executeAsync();
    }

    @Override
    protected void initUI() {

        infoLabel = new VisLabel("Pending...");
        backButton = new VisTextButton("Back");

        add(infoLabel).width(300).pad(10);
        row();
        add(backButton).width(300).pad(10);
    }

    @Override
    protected void initUIListeners() {

        backButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                navigation.showHomePage();
            }
        });
    }
}
