package shesh.tron;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kotcrab.vis.ui.VisUI;
import shesh.tron.screen.FirstScreen;
import shesh.tron.screen.MenuScreen;
import shesh.tron.screen.Navigation;
import shesh.tron.screen.auth.LoginScreen;
import shesh.tron.screen.auth.RegisterScreen;
import shesh.tron.utils.AccountUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game implements Navigation {

    private SpriteBatch batch;
    private Texture image;

    private Screen screen = new FirstScreen(this);

    @Override
    public void create() {

        VisUI.load();

        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();

        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {

        batch.dispose();
        image.dispose();
    }

    @Override
    public void showRegisterScreen() {

        screen.dispose();
        screen = new RegisterScreen(this);
        setScreen(screen);
    }

    @Override
    public void showLoginScreen() {

        screen.dispose();
        screen = new LoginScreen(this);
        setScreen(screen);
    }

    @Override
    public void showMenuScreen(String token) {

        AccountUtils.setToken(token);

        screen.dispose();
        screen = new MenuScreen(this, token);
        setScreen(screen);
    }
}
