package shesh.tron.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen extends ScreenAdapter {

    protected Navigation navigation;
    protected Stage uiStage;

    protected AbstractScreen(Navigation navigation) {

        this.navigation = navigation;
        uiStage = new Stage();

        initUI();
        initUIListeners();
        setInputProcessor();
    }

    protected abstract void initUI();

    protected abstract void initUIListeners();

    protected void setInputProcessor() {

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {

        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        uiStage.dispose();
    }
}
