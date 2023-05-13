package pl.prasulaspzoo.server.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.queue.QueueMsg;

import java.util.Map;
import java.util.Queue;

@AllArgsConstructor
@Getter
public class ServerInfo {

    private final String serverId;
    private final Queue<QueueMsg> queue;
    private final Map<String, WebSocketSession> connections;
    private final Map<String, WebSocketSession> connectionRequests;
    private final int tickrate;

    public void connectionRequest(WebSocketSession session) {
        connectionRequests.put(session.getId(), session);
        queue.add(new QueueMsg(session,
                "{" +
                    "\"msgType\": \"connectionRequest\"," +
                    "\"uid\": \"" + session.getId() + "\"" +
                "}"));
    }

    public void disconnect(WebSocketSession session) {
        queue.add(new QueueMsg(session,
                "{" +
                    "\"msgType\": \"disconnect\"," +
                    "\"uid\": \"" + session.getId() + "\"" +
                "}"));
    }
}
