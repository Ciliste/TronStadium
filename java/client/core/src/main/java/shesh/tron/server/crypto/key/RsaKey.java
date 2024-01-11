package shesh.tron.server.crypto.key;

import java.io.Serializable;
import java.math.BigInteger;

public interface RsaKey extends Serializable {

    public abstract BigInteger getN();
    public abstract BigInteger getOtherValue();
}
