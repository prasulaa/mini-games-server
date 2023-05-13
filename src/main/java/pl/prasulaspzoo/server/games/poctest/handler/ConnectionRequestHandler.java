package pl.prasulaspzoo.server.games.poctest.handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.poctest.PoCTestGameInfo;
import pl.prasulaspzoo.server.games.poctest.messages.ConnectionRequest;
import pl.prasulaspzoo.server.games.poctest.messages.GeneralMsg;
import pl.prasulaspzoo.server.games.poctest.messages.PlayerDTO;
import pl.prasulaspzoo.server.games.poctest.model.Player;
import pl.prasulaspzoo.server.manager.ServerInfo;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class ConnectionRequestHandler implements MessageHandler {

    private final ServerInfo serverInfo;
    private final PoCTestGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        ConnectionRequest msg = (ConnectionRequest) generalMsg;
        WebSocketSession requestSession = serverInfo.getConnectionRequests().remove(msg.getUid());
        serverInfo.getConnections().put(msg.getUid(), requestSession);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0f, 0f);
        bodyDef.fixedRotation = true;

        Body body = gameInfo.getWorld().createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);
        Fixture fixture = body.createFixture(shape, 1f);
        shape.dispose();

        FrictionJointDef jointDef = new FrictionJointDef();
        jointDef.maxForce = 1f;
        jointDef.maxTorque = 1f;
        jointDef.initialize(body, gameInfo.getGround().getBody(), new Vector2(0, 0));
        FrictionJoint frictionJoint = (FrictionJoint) gameInfo.getWorld().createJoint(jointDef);

        Player player = new Player(requestSession.getId(), fixture, frictionJoint);
        gameInfo.getPlayers().put(player.getId(), player);

        try {
            String playerInfoMsg = new ObjectMapper().writeValueAsString(PlayerDTO.fromPlayer(player));
            requestSession.sendMessage(new TextMessage(playerInfoMsg));

            return;
        } catch (JsonProcessingException e) {
            log.error("Exception occurred during sending init player info", e);

        } catch (IOException ex) {
            log.error("", ex);
        }

        try {
            requestSession.close(CloseStatus.SERVER_ERROR);
        } catch (IOException ex) {
            log.error("", ex);
        }
    }

}
