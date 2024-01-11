package shesh.tron.server.auth.credentials;

public interface Credentials {

    public String getUsername();
    public String getPassword();

    public void setUsername(String username);
    public void setPassword(String password);
}
