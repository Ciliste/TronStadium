package shesh.tron.server.game.logic;

import com.esotericsoftware.kryo.Kryo;
import shesh.tron.server.game.logic.net.packet.request.RequestPacket;

public final class KryoClasses {

    private KryoClasses() {
        super();
    }

    public static void registerAll(Kryo kryo) {

        kryo.register(RequestPacket.class);
    }
}
