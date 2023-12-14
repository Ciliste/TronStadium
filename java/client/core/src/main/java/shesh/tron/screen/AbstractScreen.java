package shesh.tron.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

public abstract class AbstractScreen extends ScreenAdapter {

    protected Navigation navigation;
    protected Stage uiStage;
    protected Viewport viewport;

    protected AbstractScreen(Navigation navigation) {

        this.navigation = navigation;
        uiStage = new Stage();
        viewport = new ScreenViewport();
        uiStage.setViewport(viewport);

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

    protected void handleException(Exception e) {

        e.printStackTrace();
        Dialogs.showDetailsDialog(uiStage, "Something went wrong...", "Error", e.getMessage());
    }
}
