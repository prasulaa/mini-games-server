package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerStateDTO;
import pl.prasulaspzoo.server.games.cyberwarriors.model.Player;

@AllArgsConstructor
public class PlayerStateDTOHandler implements MessageHandler {
    private static final float IMPULSE = 20f;

    private final CyberWarriorsGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        PlayerStateDTO msg = (PlayerStateDTO) generalMsg;

        Player player = gameInfo.getPlayers().get(session.getId());

        Vector2 playerPos = player.getPosition();
        float movX = (msg.getX() - playerPos.x) * IMPULSE;
        float movY = (msg.getY() - playerPos.y) * IMPULSE;

        // TODO player on the server moves really slow

        player.getFixture().getBody().setLinearVelocity(movX, movY);
    }

}
