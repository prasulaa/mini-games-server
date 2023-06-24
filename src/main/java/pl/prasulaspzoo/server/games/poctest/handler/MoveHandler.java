package pl.prasulaspzoo.server.games.poctest.handler;

import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.poctest.PoCTestGameInfo;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.poctest.messages.Move;
import pl.prasulaspzoo.server.games.poctest.model.Player;
import pl.prasulaspzoo.server.manager.ServerInfo;

@AllArgsConstructor
public class MoveHandler implements MessageHandler {

    private static final float IMPULSE = 1f;
    private static final float FLOAT_ERR = 0.00001f;

    private final ServerInfo serverInfo;
    private final PoCTestGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        Move move = (Move) generalMsg;
        float x = move.getX();
        float y = move.getY();

        if (Math.sqrt(x*x + y*y) - 1f < FLOAT_ERR) {
            Player player = gameInfo.getPlayers().get(session.getId());
            player.getFixture().getBody().setLinearVelocity(x * IMPULSE, y * IMPULSE);
        }
    }

}
