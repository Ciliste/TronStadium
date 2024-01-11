package shesh.tron.server.game.logic.net.packet.request;

public abstract class RequestPacket {

    private int requestId;

    public RequestPacket(int requestId) {

        super();
        this.requestId = requestId;
    }

    public int getRequestId() {

        return requestId;
    }
}
