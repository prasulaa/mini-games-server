package pl.prasulaspzoo.server.games.poctest.handler;

import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.poctest.messages.GeneralMsg;

public interface MessageHandler {

    void handle(GeneralMsg generalMsg, WebSocketSession session);

}
