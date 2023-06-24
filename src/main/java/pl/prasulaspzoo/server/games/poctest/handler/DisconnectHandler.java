package pl.prasulaspzoo.server.games.poctest.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.poctest.model.Player;
import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.games.poctest.PoCTestGameInfo;
import pl.prasulaspzoo.server.games.poctest.messages.Disconnect;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class DisconnectHandler implements MessageHandler {

    private final ServerInfo serverInfo;
    private final PoCTestGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        Disconnect msg = (Disconnect) generalMsg;
        try {
            serverInfo.getConnections().remove(msg.getUid()).close(CloseStatus.NORMAL);
            Player player = gameInfo.getPlayers().remove(msg.getUid());
            gameInfo.getWorld().destroyJoint(player.getFrictionJoint());
            gameInfo.getWorld().destroyBody(player.getFixture().getBody());
        } catch (IOException e) {
            log.error("Exception occurred while closing the socket", e);
        }
    }

}
