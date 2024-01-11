package shesh.tron.screen.auth;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import shesh.tron.screen.AbstractScreen;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.navigation.Navigation;
import shesh.tron.utils.APIUtils;
import shesh.tron.utils.logger.LoggerFactory;

public class LoginScreen extends AbstractScreen {

    private VisTextField usernameField;
    private VisTextField passwordField;

    private VisTextButton loginButton;
    private VisTextButton registerButton;
    private VisTextButton backButton;

    private final ApiEndpointContext apiEndpointContext;

    public LoginScreen(Navigation navigation, ApiEndpointContext apiEndpointContext) {

        super(navigation);
        this.apiEndpointContext = apiEndpointContext;
    }

    @Override
    protected void initUI() {

        VisTable table = new VisTable();
        table.setFillParent(true);

        usernameField = new VisTextField();
        usernameField.setMessageText("Username");

        passwordField = new VisTextField("Password");
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setText("");

        loginButton = new VisTextButton("Login");
        registerButton = new VisTextButton("Register");
        backButton = new VisTextButton("Back");

        table.add(usernameField).width(300).pad(10);
        table.row();

        table.add(passwordField).width(300).pad(10);
        table.row();

        table.add(loginButton).width(300).pad(10);
        table.row();

        table.add(registerButton).width(300).pad(10);
        table.row();

        table.add(backButton).width(300).pad(10);

        uiStage.addActor(table);
    }

    @Override
    protected void initUIListeners() {

        initLoginButtonListener();
        initRegisterButtonListener();
        initBackButtonListener();
    }

    private void initLoginButtonListener() {

        loginButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                String username = usernameField.getText();
                String password = passwordField.getText();

                apiEndpointContext.getApiEndpoint().login(username, password).then(token -> {

                    if (null != token) {

                        LoggerFactory.getLogger().info("Login successful, showing menu screen");
                        navigation.showMenuScreen(token.getToken());
                    }
                    else {

                        Dialogs.showErrorDialog(uiStage, "Login failed", "Username or password is incorrect");
                    }

                }).executeAsync();
            }
        });
    }

    private void initRegisterButtonListener() {

        registerButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                navigation.showRegisterScreen();
            }
        });
    }

    private void initBackButtonListener() {

        backButton.addListener(new ClickListener() {

        });
    }
}
