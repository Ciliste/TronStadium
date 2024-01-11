package shesh.tron.server.auth.user;

public class UserImpl implements User {

    private String id;
    private String username;
    private String createdAt;

    public UserImpl(String id, String username, String createdAt) {

        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {

        return id;
    }

    @Override
    public String getUsername() {

        return username;
    }

    @Override
    public String getCreatedAt() {

        return createdAt;
    }

    @Override
    public String toString() {

        return "UserImpl{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
