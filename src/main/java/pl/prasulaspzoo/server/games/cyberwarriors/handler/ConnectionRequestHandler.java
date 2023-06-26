package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerDTO;
import pl.prasulaspzoo.server.manager.ServerInfo;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class ConnectionRequestHandler implements MessageHandler {

    private final ServerInfo serverInfo;
    private final CyberWarriorsGameInfo gameInfo;


    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        // TODO
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(session.getId());
        playerDTO.setX(3f);
        playerDTO.setY(2f);

        try {
            String playerInfoMsg = objectMapper.writeValueAsString(playerDTO);
            session.sendMessage(new TextMessage(playerInfoMsg));
            return;
        } catch (IOException e) {
            log.error("Exception occurred, ws session=" + session, e);
        }

        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (IOException ex) {
            log.error("Exception occurred during connection closing, ws session=" + session, ex);
        }
    }

}
