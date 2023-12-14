package shesh.tron.screen.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import shesh.tron.screen.panel.AbstractPanel;
import shesh.tron.utils.APIUtils;

public class SettingsPanel extends AbstractPanel {

    private VisLabel infoLabel;
    private VisTextButton backButton;

    private MainMenuNavigation navigation;

    public SettingsPanel(String token, MainMenuNavigation navigation) {

        super();

        this.navigation = navigation;

        APIUtils.getUserInfo(token).then(response -> {

            infoLabel.setText(response.getRawResponse());

        }).catchError(error -> {

            infoLabel.setText(error.getMessage());

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
