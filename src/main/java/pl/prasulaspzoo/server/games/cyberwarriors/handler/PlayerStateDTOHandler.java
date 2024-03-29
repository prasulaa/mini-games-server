package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerStateDTO;
import pl.prasulaspzoo.server.games.cyberwarriors.model.Player;
import pl.prasulaspzoo.server.manager.ServerInfo;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class PlayerStateDTOHandler implements MessageHandler {

    private static final float FLOAT_ERR = 1e-6f;
    private final ServerInfo serverInfo;
    private final CyberWarriorsGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        PlayerStateDTO msg = (PlayerStateDTO) generalMsg;
        Player player = gameInfo.getPlayers().get(session.getId());

        if (player != null) {
            movePlayer(player, msg);
        } else {
            closeSession(session);
        }
    }

    public void movePlayer(Player player, PlayerStateDTO msg) {
        float impulse = serverInfo.getTickrate();
        Vector2 playerPos = player.getPosition();

        float movX = Math.abs(msg.getX() - playerPos.x) > FLOAT_ERR ? msg.getX() - playerPos.x : 0f;
        float movY = Math.abs(msg.getY() - playerPos.y) > FLOAT_ERR ? msg.getY() - playerPos.y : 0f;
        double r = Math.sqrt(movX*movX + movY*movY);

        player.getFixture().getBody().setLinearVelocity(
                movX * impulse,
                movY * impulse
        );
    }

    public void closeSession(WebSocketSession session) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("abc", e);
        }
    }

}
