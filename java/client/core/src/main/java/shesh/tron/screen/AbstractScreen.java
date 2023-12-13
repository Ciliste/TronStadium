package shesh.tron.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class AbstractScreen extends ScreenAdapter {

    protected Navigation navigation;

    protected AbstractScreen(Navigation navigation) {

        this.navigation = navigation;
    }
}
