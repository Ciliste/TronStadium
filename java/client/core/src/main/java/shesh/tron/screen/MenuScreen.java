package shesh.tron.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen extends AbstractScreen {

    private SpriteBatch batch;

    public MenuScreen(Navigation navigation, String token) {

        super(navigation);

        batch = new SpriteBatch();


    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initUIListeners() {

    }

    @Override
    public void render(float delta) {

        super.render(delta);
    }

    @Override
    public void dispose() {

        super.dispose();
    }
}
