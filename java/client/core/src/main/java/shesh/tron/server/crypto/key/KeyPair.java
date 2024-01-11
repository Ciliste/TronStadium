package shesh.tron.server.crypto.key;

import shesh.tron.utils.struct.Couple;

public interface KeyPair extends Couple<PublicKey, PrivateKey> {

    public abstract PublicKey getPublicKey();
    public abstract PrivateKey getPrivateKey();
}
