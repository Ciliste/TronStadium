package shesh.tron.screen;

import com.badlogic.gdx.ScreenAdapter;
import shesh.tron.utils.APIUtils;
import shesh.tron.utils.AccountUtils;

public class FirstScreen extends ScreenAdapter {

    private Navigation navigation;

    public FirstScreen(Navigation navigation) {

        this.navigation = navigation;
    }

    @Override
    public void render(float delta) {

        super.render(delta);

        String token = AccountUtils.getToken();
        if (null != token) {

            boolean isTokenValid = false;
            try {

                isTokenValid = APIUtils.isValidToken(token);
            }
            catch (Exception ignored) {}

            if (isTokenValid) {

                navigation.showMenuScreen(token);
            }
            else {

                navigation.showLoginScreen();
            }
        }
        else {

            navigation.showLoginScreen();
        }
    }
}
