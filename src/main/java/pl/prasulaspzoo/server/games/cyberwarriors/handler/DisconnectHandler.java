package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.common.message.Disconnect;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.model.Player;
import pl.prasulaspzoo.server.manager.ServerInfo;

@AllArgsConstructor
public class DisconnectHandler implements MessageHandler {

    private final ServerInfo serverInfo;
    private final CyberWarriorsGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        Disconnect msg = (Disconnect) generalMsg;

        serverInfo.getConnections().remove(session.getId());

        Player player = gameInfo.getPlayers().remove(msg.getUid());
        player.dispose();
    }

}
