package shesh.tron.screen;

import com.badlogic.gdx.ScreenAdapter;

public class FirstScreen extends ScreenAdapter {

    private Navigation navigation;

    public FirstScreen(Navigation navigation) {

        this.navigation = navigation;
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        navigation.showRegisterScreen();
    }
}
