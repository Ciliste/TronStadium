package shesh.tron.screen.auth;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import shesh.tron.screen.AbstractScreen;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.navigation.Navigation;
import shesh.tron.utils.APIUtils;

public class RegisterScreen extends AbstractScreen {

    private VisTextField usernameField;
    private VisTextField passwordField;
    private VisTextField confirmPasswordField;

    private VisTextButton registerButton;
    private VisTextButton loginButton;
    private VisTextButton backButton;

    private final ApiEndpointContext apiEndpointContext;

    public RegisterScreen(Navigation navigation, ApiEndpointContext apiEndpointContext) {

        super(navigation);
        this.apiEndpointContext = apiEndpointContext;
    }

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

        confirmPasswordField = new VisTextField("Confirm Password");
        confirmPasswordField.setMessageText("Confirm Password");
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        confirmPasswordField.setText("");

        registerButton = new VisTextButton("Register");
        loginButton = new VisTextButton("Login");
        backButton = new VisTextButton("Back");

        table.add(usernameField).width(300).pad(10);
        table.row();
        table.add(passwordField).width(300).pad(10);
        table.row();
        table.add(confirmPasswordField).width(300).pad(10);
        table.row();
        table.add(registerButton).width(300).pad(10);
        table.row();
        table.add(loginButton).width(300).pad(10);
        table.row();
        table.add(backButton).width(300).pad(10);

        uiStage.addActor(table);
    }

    protected void initUIListeners() {

        initRegisterButtonListener();
        initLoginButtonListener();
    }

    private void initRegisterButtonListener() {

        registerButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

                    Dialogs.showErrorDialog(uiStage, "Please fill all the fields.");
                }
                else if (!password.equals(confirmPassword)) {

                    Dialogs.showErrorDialog(uiStage, "Passwords do not match.");
                }
                else {

                    apiEndpointContext.getApiEndpoint().register(username, password).then(token -> {

                        if (null != token) {

                            navigation.showMenuScreen(token.getToken());
                        }
                        else {

                            Dialogs.showErrorDialog(uiStage, "Could not register.");
                        }

                    }).executeAsync();
                }
            }
        });
    }

    private void initLoginButtonListener() {

        loginButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                navigation.showLoginScreen();
            }
        });
    }
}
