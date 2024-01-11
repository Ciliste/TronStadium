package shesh.tron.screen.context;

public interface TokenContext extends Context {

    default void setToken(String token) {

        set("token", token);
    }

    default String getToken() {

        return (String) get("token");
    }
}
