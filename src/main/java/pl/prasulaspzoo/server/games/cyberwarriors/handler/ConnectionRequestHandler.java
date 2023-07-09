package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.common.message.ConnectionRequest;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerDTO;
import pl.prasulaspzoo.server.games.cyberwarriors.model.Player;
import pl.prasulaspzoo.server.manager.ServerInfo;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class ConnectionRequestHandler implements MessageHandler {
    private static final float INIT_X = 3f;
    private static final float INIT_Y = 2f;

    private final ServerInfo serverInfo;
    private final CyberWarriorsGameInfo gameInfo;

    @Override
    public void handle(GeneralMsg generalMsg, WebSocketSession session) {
        ConnectionRequest msg = (ConnectionRequest) generalMsg;
        WebSocketSession requestSession = serverInfo.getConnectionRequests().remove(session.getId());
        serverInfo.getConnections().put(msg.getUid(), requestSession);

        Body body = createBody();
        body.setFixedRotation(true);
        Fixture fixture = createFixture(body);
        FrictionJoint frictionJoint = createFrictionJoint(body);

        gameInfo.getPlayers().put(
                session.getId(),
                new Player(fixture, frictionJoint, gameInfo.getWorld())
        );

        sendPlayerInfo(session);
    }

    private void sendPlayerInfo(WebSocketSession session) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(session.getId());
        playerDTO.setX(INIT_X);
        playerDTO.setY(INIT_Y);

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

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(INIT_X, INIT_Y);

        return gameInfo.getWorld().createBody(bodyDef);
    }

    private Fixture createFixture(Body body) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.07f;
        fixtureDef.restitution = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        return fixture;
    }

    private FrictionJoint createFrictionJoint(Body body) {
        FrictionJointDef jointDef = new FrictionJointDef();
        jointDef.maxForce = 10f;
        jointDef.maxTorque = 10f;
        jointDef.initialize(body, gameInfo.getBackgroundFixture().getBody(), new Vector2(0, 0));
        return (FrictionJoint) gameInfo.getWorld().createJoint(jointDef);
    }

}
