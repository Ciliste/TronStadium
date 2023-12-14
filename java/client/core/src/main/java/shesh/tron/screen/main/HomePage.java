package shesh.tron.screen.main;

import com.kotcrab.vis.ui.widget.VisLabel;
import shesh.tron.screen.panel.AbstractPanel;
import shesh.tron.utils.APIUtils;

public class HomePage extends AbstractPanel {

    private VisLabel serverNameLabel;


    public HomePage(MainMenuNavigation navigation) {

        super();

        APIUtils.getServerInfo().then(response -> {

            serverNameLabel.setText(response.getRawResponse());

        }).catchError(error -> {

            serverNameLabel.setText(error.getMessage());

        }).executeAsync();
    }

    @Override
    protected void initUI() {

        serverNameLabel = new VisLabel("Pending...");

        add(serverNameLabel).width(300).pad(10);
    }

    @Override
    protected void initUIListeners() {

    }
}
