package shesh.tron.screen.main;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.*;
import shesh.tron.screen.AbstractScreen;
import shesh.tron.screen.Navigation;
import shesh.tron.utils.AccountUtils;

public class MenuScreen extends AbstractScreen implements MainMenuNavigation {

    private VisTextButton profileButton;
    private VisTextButton profileSettingsButton;
    private VisList<String> friendsList;
    private VisScrollPane friendsListScrollPane;
    private VisTextButton logoutButton;

    private VisWindow centerWindow;

    private String token;

    public MenuScreen(Navigation navigation, String token) {

        super(navigation);

        this.token = token;
    }

    @Override
    protected void initUI() {

        profileButton = new VisTextButton("Profile");
        profileSettingsButton = new VisTextButton("Profile Settings");

        friendsList = new VisList<String>();

        String[] friends = new String[100];
        for (int i = 1; i <= 100; i++) {
            friends[i - 1] = "Friend " + i;
        }

        friendsList.setItems(friends);
        logoutButton = new VisTextButton("Logout");

        changeCenterWindow(new HomePage(this));

        uiStage.addActor(profileButton);
        uiStage.addActor(profileSettingsButton);

        friendsListScrollPane = new VisScrollPane(friendsList);
        friendsListScrollPane.setScrollingDisabled(true, false);
        uiStage.addActor(friendsListScrollPane);

        uiStage.addActor(logoutButton);
    }

    @Override
    protected void initUIListeners() {

        initLogoutButtonListener();
        initProfileSettingsButtonListener();
    }

    private void initLogoutButtonListener() {

        logoutButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                AccountUtils.deleteToken();
                navigation.showLoginScreen();
            }
        });
    }

    private void initProfileSettingsButtonListener() {

        profileSettingsButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                changeCenterWindow(new SettingsPanel(token, MenuScreen.this));
            }
        });
    }

    private void changeCenterWindow(VisWindow window) {

        uiStage.getRoot().removeActor(centerWindow);
        centerWindow = window;
        uiStage.addActor(centerWindow);

        centerWindow.setBounds(uiStage.getWidth() * 0.2f, 0, uiStage.getWidth() * 0.8f, uiStage.getHeight());
    }

    @Override
    public void render(float delta) {

        super.render(delta);
    }

    @Override
    public void dispose() {

        super.dispose();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);

        final float LEFT_MENU_WIDTH = 0.2f;

        final float LEFT_BUTTON_HEIGHT = 0.1f;
        final int LEFT_BUTTON_HEIGHT_PIXELS = (int) (height * LEFT_BUTTON_HEIGHT);

        profileSettingsButton.setBounds(width * LEFT_MENU_WIDTH - LEFT_BUTTON_HEIGHT_PIXELS, height - LEFT_BUTTON_HEIGHT_PIXELS, LEFT_BUTTON_HEIGHT_PIXELS, LEFT_BUTTON_HEIGHT_PIXELS);
        profileButton.setBounds(0, height - LEFT_BUTTON_HEIGHT_PIXELS, width * LEFT_MENU_WIDTH - profileSettingsButton.getWidth(), LEFT_BUTTON_HEIGHT_PIXELS);

        logoutButton.setBounds(0, 0, width * LEFT_MENU_WIDTH, LEFT_BUTTON_HEIGHT_PIXELS);

        friendsListScrollPane.setBounds(0, LEFT_BUTTON_HEIGHT_PIXELS, width * LEFT_MENU_WIDTH, height - LEFT_BUTTON_HEIGHT_PIXELS * 2);

        centerWindow.setBounds(width * LEFT_MENU_WIDTH, 0, width * (1 - LEFT_MENU_WIDTH), height);
    }

    @Override
    public void showHomePage() {

        changeCenterWindow(new HomePage(this));
    }
}
