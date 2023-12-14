package shesh.tron.screen;

public interface Navigation {

    public abstract void showRegisterScreen();
    public abstract void showLoginScreen();

    public abstract void showMenuScreen(String token);
}
