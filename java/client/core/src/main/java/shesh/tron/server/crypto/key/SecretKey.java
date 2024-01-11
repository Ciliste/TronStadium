package shesh.tron.server.crypto.key;

import shesh.tron.server.crypto.key.impl.SecretKeyImpl;

import java.io.Serializable;

public interface SecretKey extends Serializable {

    public abstract String getKey();
}
