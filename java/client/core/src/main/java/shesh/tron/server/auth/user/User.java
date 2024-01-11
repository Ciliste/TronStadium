package shesh.tron.server.auth.user;

import java.io.Serializable;

public interface User extends Serializable {

    public abstract String getId();
    public abstract String getUsername();
    public abstract String getCreatedAt();
}
