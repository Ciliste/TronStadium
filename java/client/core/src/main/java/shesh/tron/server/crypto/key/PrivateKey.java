package shesh.tron.server.crypto.key;

import java.math.BigInteger;

public interface PrivateKey extends RsaKey {

    public abstract BigInteger getN();
    public abstract BigInteger getD();
}
