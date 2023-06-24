package pl.prasulaspzoo.server.games.common;

import org.springframework.web.socket.WebSocketSession;

public interface MessageHandler {

    void handle(GeneralMsg generalMsg, WebSocketSession session);

}
