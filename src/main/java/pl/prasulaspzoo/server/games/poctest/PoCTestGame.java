package pl.prasulaspzoo.server.games.poctest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import pl.prasulaspzoo.server.games.common.Game;
import pl.prasulaspzoo.server.games.poctest.handler.MessageHandlerFactory;
import pl.prasulaspzoo.server.games.poctest.messages.PlayerDTO;
import pl.prasulaspzoo.server.games.poctest.messages.WorldInfo;
import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.queue.QueueDispatcher;
import pl.prasulaspzoo.server.queue.QueueMsg;

import java.io.IOException;
import java.util.Queue;
import java.util.stream.Collectors;

public class PoCTestGame extends Game {

    private static final Logger log = LoggerFactory.getLogger(PoCTestGame.class);
    private final ObjectMapper objectMapper;
    private final ObjectReader msgReader;
    private MessageHandlerFactory handlerFactory;
    private PoCTestGameInfo gameInfo;

    private long lastRunTime = System.currentTimeMillis();

    public PoCTestGame(ServerInfo serverInfo, QueueDispatcher queueDispatcher) {
        super(serverInfo, queueDispatcher);
        objectMapper = new ObjectMapper();
        msgReader = objectMapper.readerFor(GeneralMsg.class);
    }

    @Override
    public void init() {
        World world = new World(new Vector2(0, 0), true);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(5, 5));
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50, 50);
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundBox;
        groundFixtureDef.density = 0.0f;
        groundFixtureDef.isSensor = true;

        Fixture fixture = groundBody.createFixture(groundFixtureDef);
        groundBox.dispose();

        gameInfo = new PoCTestGameInfo(world, fixture);
        handlerFactory = new MessageHandlerFactory(serverInfo, gameInfo);

        log.info(serverInfo.getServerId() + " - PoCTestGame initialized");
    }

    @Override
    public void run() {
        Queue<QueueMsg> queue = serverInfo.getQueue();

        while (!queue.isEmpty()) {
            QueueMsg queueMsg = queue.poll();
            try {
                String msg = queueMsg.getMsg();
                log.info(serverInfo.getServerId() + " - " + queueMsg.getSession().getId() + " - " + msg);

                // HANDLE MSG
                GeneralMsg objectMsg = msgReader.readValue(msg);
                handlerFactory.messageHandler(objectMsg).handle(objectMsg, queueMsg.getSession());

                // SHUTDOWN
                if (msg.equals("shutdown")) {
                    log.info(serverInfo.getServerId() + " - shutdown");
                    shutdown();
                }
            } catch (JsonProcessingException e) {
                log.error("Exception occurred while parsing json", e);
                try {
                    queueMsg.getSession().close(CloseStatus.BAD_DATA);
                } catch (IOException ex) {
                    log.error("Exception occurred while closing the socket", e);
                }
            } catch (Exception e) {
                log.error("Unexpected exception occurred", e);
                try {
                    queueMsg.getSession().close(CloseStatus.SERVER_ERROR);
                } catch (IOException ex) {
                    log.error("Exception occurred while closing the socket", e);
                }
            }
        }

        // STEP WORLD
        gameInfo.getWorld().step(deltaTime(), 6, 2);

        // SEND MSG
        WorldInfo worldInfo = new WorldInfo(
                gameInfo.getPlayers().values().stream()
                        .map(player -> {
                            Vector2 position = player.getFixture().getBody().getPosition();
                            return new PlayerDTO(player.getId(), position.x, position.y);
                        })
                        .collect(Collectors.toList())
        );
        try {
            String json = objectMapper.writeValueAsString(worldInfo);
            serverInfo.getConnections().values().forEach(session -> {
                try {
                    session.sendMessage(new TextMessage(json));
                } catch (IOException e) {
                    log.error("Error occurred during sending to player", e);
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private float deltaTime() {
        long now = System.currentTimeMillis();
        float delta = (now - lastRunTime) / 1000f;
        lastRunTime = now;
        return delta;
    }

}
