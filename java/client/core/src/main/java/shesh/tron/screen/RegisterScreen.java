package shesh.tron.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import shesh.tron.utils.APIUtils;

public class RegisterScreen extends AbstractScreen {

    private Stage stage;

    private VisTextField usernameField;
    private VisTextField passwordField;
    private VisTextField confirmPasswordField;

    private VisTextButton registerButton;
    private VisTextButton loginButton;
    private VisTextButton backButton;

    public RegisterScreen(Navigation navigation) {

        super(navigation);

        initUI();
        initUIListeners();
        setPositions();
        setInputProcessor();
    }

    private void initUI() {

        stage = new Stage();

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

        stage.addActor(table);
    }

    private void initUIListeners() {

        initRegisterButtonListener();
    }

    private void initRegisterButtonListener() {

        registerButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

                    Dialogs.showErrorDialog(stage, "Please fill all the fields.");
                }
                else if (!password.equals(confirmPassword)) {

                    Dialogs.showErrorDialog(stage, "Passwords do not match.");
                }
                else {

                    try {

                        System.out.println(APIUtils.register(username, password));
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setInputProcessor() {

        Gdx.input.setInputProcessor(stage);
    }

    private void setPositions() {


    }

    @Override
    public void render(float delta) {

        super.render(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        super.dispose();
    }
}
