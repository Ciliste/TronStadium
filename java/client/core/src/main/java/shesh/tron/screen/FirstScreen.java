package shesh.tron.screen;

public class FirstScreen extends AbstractScreen {

    public FirstScreen(Navigation navigation) {

        super(navigation);
    }

    @Override
    public void render(float delta) {

        super.render(delta);

        navigation.showRegisterScreen();
    }

    @Override
    public void dispose() {

        super.dispose();
    }
}
