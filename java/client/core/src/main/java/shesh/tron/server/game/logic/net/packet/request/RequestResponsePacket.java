package shesh.tron.server.game.logic.net.packet.request;

public class RequestResponsePacket {

    private int requestId;
    private Object response;

    public RequestResponsePacket(int requestId, Object response) {

        super();
        this.requestId = requestId;
        this.response = response;
    }

    public int getRequestId() {

        return requestId;
    }

    public Object getResponse() {

        return response;
    }
}
