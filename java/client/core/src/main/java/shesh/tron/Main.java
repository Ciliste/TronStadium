package shesh.tron;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kotcrab.vis.ui.VisUI;
import shesh.tron.constants.ServerUrls;
import shesh.tron.screen.FirstScreen;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.context.Context;
import shesh.tron.screen.context.TokenContext;
import shesh.tron.screen.main.MenuScreen;
import shesh.tron.screen.navigation.Navigation;
import shesh.tron.screen.auth.LoginScreen;
import shesh.tron.screen.auth.RegisterScreen;
import shesh.tron.utils.AppDataUtils;
import shesh.tron.utils.logger.LoggerFactory;
import shesh.tron.utils.logger.impl.ClientLogger;
import shesh.tron.utils.logger.impl.StackTraceLoggerDecorator;
import shesh.tron.utils.logger.impl.TerminalLoggerDecorator;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game implements Navigation, Context, ApiEndpointContext, TokenContext {

    private SpriteBatch batch;
    private Texture image;

    private Screen screen = new FirstScreen(this, this);

    public Main(String serverUrl) {

        ServerUrls.updateUrls(serverUrl);
    }

    @Override
    public void create() {

        VisUI.load();

        LoggerFactory.setLogger(new TerminalLoggerDecorator(new StackTraceLoggerDecorator(new ClientLogger("client.log"))));

        LoggerFactory.getLogger().info("Client started");

        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        AppDataUtils.load();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);
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

        Gdx.app.postRunnable(() -> {
            screen.dispose();
            screen = new RegisterScreen(this, this);
            setScreen(screen);
        });
    }

    @Override
    public void showLoginScreen() {

        Gdx.app.postRunnable(() -> {
            screen.dispose();
            screen = new LoginScreen(this, this);
            setScreen(screen);
        });
    }

    @Override
    public void showMenuScreen(String token) {

        AppDataUtils.set("token", token);
        setToken(token);

        Gdx.app.postRunnable(() -> {
            screen.dispose();
            screen = new MenuScreen(this, token, this, this);
            setScreen(screen);
        });
    }

    private final Map<String, Object> context = new HashMap<>();

    @Override
    public void set(String key, Object value) {

        context.put(key, value);
    }

    @Override
    public Object get(String key) {

        return context.get(key);
    }
}
