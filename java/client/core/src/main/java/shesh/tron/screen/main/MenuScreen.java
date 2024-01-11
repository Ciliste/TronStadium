package shesh.tron.screen.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.InputDialogListener;
import com.kotcrab.vis.ui.widget.*;
import shesh.tron.screen.AbstractScreen;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.context.TokenContext;
import shesh.tron.screen.game_lobby.PlayPanel;
import shesh.tron.screen.main.friend.FriendRequestsPopup;
import shesh.tron.screen.navigation.Navigation;
import shesh.tron.server.auth.token.Token;
import shesh.tron.utils.AccountUtils;
import shesh.tron.utils.logger.LoggerFactory;

import java.util.Arrays;

public class MenuScreen extends AbstractScreen implements MainMenuNavigation {

    private VisTextButton profileButton;
    private VisTextButton profileSettingsButton;
    private VisList<String> friendsList;
    private VisScrollPane friendsListScrollPane;
    private VisTextButton logoutButton;

    private VisTextButton addFriendButton;
    private VisTextButton friendRequestButton;

    private VisWindow centerWindow;

    private String token;

    private final ApiEndpointContext apiEndpointContext;
    private final TokenContext tokenContext;

    public MenuScreen(Navigation navigation, String token, ApiEndpointContext apiEndpointContext, TokenContext tokenContext) {

        super(navigation);

        this.token = token;
        this.apiEndpointContext = apiEndpointContext;
        this.tokenContext = tokenContext;
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

        addFriendButton = new VisTextButton("Add Friend");
        friendRequestButton = new VisTextButton("Friend Requests");

        showHomePage();

        uiStage.addActor(profileButton);
        uiStage.addActor(profileSettingsButton);

        uiStage.addActor(addFriendButton);
        uiStage.addActor(friendRequestButton);

        friendsListScrollPane = new VisScrollPane(friendsList);
        friendsListScrollPane.setScrollingDisabled(true, false);
        uiStage.addActor(friendsListScrollPane);

        uiStage.addActor(logoutButton);
    }

    @Override
    protected void initUIListeners() {

        initLogoutButtonListener();
        initProfileSettingsButtonListener();
        initAddFriendButtonListener();
        initFriendRequestButtonListener();
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

    private VisWindow popupWindow;
    private final float POPUP_WINDOW_WIDTH = 0.3f;
    private final float POPUP_WINDOW_HEIGHT = 0.6f;
    private final float POPUP_WINDOW_X = 0.2f;
    private final float POPUP_WINDOW_Y = 0f;
    private final float POPUP_WINDOW_ANIMATION_DURATION = 0.2f;

    private long popupWindowAnimationStartTime = 0;

    private void setPopupWindow(VisWindow window) {

        if (popupWindow != null) {

            removePopupWindow();
        }

        popupWindow = window;
        uiStage.addActor(popupWindow);

        popupWindowAnimationStartTime = System.currentTimeMillis();
    }

    private void removePopupWindow() {

        uiStage.getRoot().removeActor(popupWindow);
        popupWindow = null;
    }

    private void initProfileSettingsButtonListener() {

        profileSettingsButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                showSettingsPage();
            }
        });
    }

    private void initAddFriendButtonListener() {

        addFriendButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

//                setPopupWindow(new DebugPanel());
                Dialogs.showInputDialog(uiStage, "Add Friend", "Enter Username#ID", new InputDialogListener() {

                    @Override
                    public void finished(String input) {

                        try {

                            String username = input.substring(0, input.indexOf("#"));
                            String id = input.substring(input.indexOf("#") + 1);

                            apiEndpointContext.getApiEndpoint().sendFriendRequest(Token.of(token), username, id).executeAsync();
                            Dialogs.showOKDialog(uiStage, "Friend Request", "Friend request sent to " + input);
                        }
                        catch (Exception e) {

                            LoggerFactory.getLogger().error("Error while sending friend request", e);
                            Dialogs.showErrorDialog(uiStage, "Error", "Error while sending friend request");
                        }
                    }

                    @Override
                    public void canceled() {


                    }
                });
            }
        });
    }

    private void initFriendRequestButtonListener() {

        friendRequestButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                apiEndpointContext.getApiEndpoint().getFriendRequests(Token.of(token))
                    .then(users -> {

                        LoggerFactory.getLogger().debug("Friend requests: " + Arrays.toString(users));
                        setPopupWindow(new FriendRequestsPopup(users, apiEndpointContext, tokenContext));
                    })
                    .error(error -> {

                        LoggerFactory.getLogger().error("Error while getting friend requests", error);
                        Dialogs.showErrorDialog(uiStage, "Error", "Error while getting friend requests");
                    })
                    .executeAsync();
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

        if (popupWindow != null) {

            float popupWindowAnimationProgress = (System.currentTimeMillis() - popupWindowAnimationStartTime) / (POPUP_WINDOW_ANIMATION_DURATION * 1000);

            if (popupWindowAnimationProgress >= 1) {

                popupWindowAnimationProgress = 1;
            }

            popupWindow.setBounds(uiStage.getWidth() * POPUP_WINDOW_X, uiStage.getHeight() * POPUP_WINDOW_Y, uiStage.getWidth() * POPUP_WINDOW_WIDTH * popupWindowAnimationProgress, uiStage.getHeight() * POPUP_WINDOW_HEIGHT * popupWindowAnimationProgress);
        }
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

        friendsListScrollPane.setBounds(0, LEFT_BUTTON_HEIGHT_PIXELS + (LEFT_BUTTON_HEIGHT_PIXELS * .66f), width * LEFT_MENU_WIDTH, height - (LEFT_BUTTON_HEIGHT_PIXELS * .66f)  - (LEFT_BUTTON_HEIGHT_PIXELS * 2));

        addFriendButton.setBounds(0, LEFT_BUTTON_HEIGHT_PIXELS, (width * LEFT_MENU_WIDTH) * .5f, LEFT_BUTTON_HEIGHT_PIXELS * .66f);
        friendRequestButton.setBounds((width * LEFT_MENU_WIDTH) * .5f, LEFT_BUTTON_HEIGHT_PIXELS, (width * LEFT_MENU_WIDTH) * .5f, LEFT_BUTTON_HEIGHT_PIXELS * .66f);

        centerWindow.setBounds(width * LEFT_MENU_WIDTH, 0, width * (1 - LEFT_MENU_WIDTH), height);
    }

    private HomePage homePage;

    @Override
    public void showHomePage() {

        if (homePage == null) {
            homePage = new HomePage(this);
        }

        changeCenterWindow(homePage);
    }

    private PlayPanel playPanel;

    @Override
    public void showPlayPage() {

        if (playPanel == null) {

            playPanel = new PlayPanel(this, apiEndpointContext, tokenContext);
        }

        changeCenterWindow(playPanel);
    }

    private SettingsPanel settingsPanel;

    public void showSettingsPage() {

        if (settingsPanel == null) {
            settingsPanel = new SettingsPanel(token, this, apiEndpointContext);
        }

        changeCenterWindow(settingsPanel);
    }
}
