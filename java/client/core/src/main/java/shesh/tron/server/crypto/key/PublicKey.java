package shesh.tron.server.crypto.key;

import java.math.BigInteger;

public interface PublicKey extends RsaKey {

    public abstract BigInteger getE();
}
