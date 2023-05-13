package pl.prasulaspzoo.server.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Getter
public class QueueMsg {

    private final WebSocketSession session;
    private final String msg;

}
