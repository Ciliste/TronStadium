package shesh.tron.screen.auth;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import shesh.tron.screen.AbstractScreen;
import shesh.tron.screen.Navigation;
import shesh.tron.utils.APIUtils;

public class LoginScreen extends AbstractScreen {

    private VisTextField usernameField;
    private VisTextField passwordField;

    private VisTextButton loginButton;
    private VisTextButton registerButton;
    private VisTextButton backButton;

    public LoginScreen(Navigation navigation) {

        super(navigation);
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

                try {

                    String token = APIUtils.login(username, password);
                    navigation.showMenuScreen(token);
                }
                catch (Exception e) {

                    Dialogs.showErrorDialog(uiStage, e.getMessage());
                    e.printStackTrace();
                }
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
