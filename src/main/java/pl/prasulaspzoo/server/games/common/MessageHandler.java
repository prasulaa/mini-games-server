package pl.prasulaspzoo.server.games.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketSession;

public interface MessageHandler {
    ObjectMapper objectMapper = new ObjectMapper();

    void handle(GeneralMsg generalMsg, WebSocketSession session);

}
