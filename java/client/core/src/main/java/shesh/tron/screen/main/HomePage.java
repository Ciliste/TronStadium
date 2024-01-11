package shesh.tron.screen.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import shesh.tron.screen.panel.AbstractPanel;
import shesh.tron.utils.APIUtils;

public class HomePage extends AbstractPanel {

    private VisLabel serverNameLabel;

    private VisTextButton playButton;

    private MainMenuNavigation navigation;

    public HomePage(MainMenuNavigation navigation) {

        super();

        this.navigation = navigation;

        // TODO: Get server name from server
    }

    @Override
    protected void initUI() {

        serverNameLabel = new VisLabel("Pending...");
        playButton = new VisTextButton("Play");

        add(serverNameLabel).width(300).pad(10);
        row();
        add(playButton).width(300).pad(10);
    }

    @Override
    protected void initUIListeners() {

        playButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                navigation.showPlayPage();
            }
        });
    }
}
