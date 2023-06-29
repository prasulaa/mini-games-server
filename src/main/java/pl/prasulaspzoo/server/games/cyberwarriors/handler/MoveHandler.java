package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.MoveDTO;

@AllArgsConstructor
public class MoveHandler implements MessageHandler {

    private final CyberWarriorsGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        MoveDTO msg = (MoveDTO) generalMsg;

        // TODO
    }

}
