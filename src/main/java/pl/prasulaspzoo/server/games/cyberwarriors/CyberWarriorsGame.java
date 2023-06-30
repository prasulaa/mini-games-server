package pl.prasulaspzoo.server.games.cyberwarriors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import pl.prasulaspzoo.server.games.common.Game;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.HandlerNotRegisteredException;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerDTO;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.WorldInfoDTO;
import pl.prasulaspzoo.server.games.cyberwarriors.handler.MessageHandlerRepository;
import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.queue.QueueDispatcher;
import pl.prasulaspzoo.server.queue.QueueMsg;

import java.io.IOException;
import java.util.Queue;

// TODO
// move handling
// sending players info to each player
// graphics
// animation

@Slf4j
public class CyberWarriorsGame extends Game {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private final ObjectMapper mapper;
    private final ObjectReader msgReader;
    private MessageHandlerRepository handlerRepository;
    private CyberWarriorsGameInfo gameInfo;
    private long lastRunTime = System.currentTimeMillis();

    public CyberWarriorsGame(ServerInfo serverInfo, QueueDispatcher queueDispatcher) {
        super(serverInfo, queueDispatcher);
        mapper = new ObjectMapper();
        msgReader = mapper.readerFor(GeneralMsg.class);
    }

    @Override
    protected void init() {
        World world = new World(new Vector2(0, -20), true);

        gameInfo = new CyberWarriorsGameInfo(world, createBackgroundFixture(world));
        handlerRepository = new MessageHandlerRepository(serverInfo, gameInfo);

        log.info(serverInfo.getServerId() + " - Cyber Warriors 2115 game initialized");
    }

    @Override
    public void run() {
        Queue<QueueMsg> queue = serverInfo.getQueue();

        while (!queue.isEmpty()) {
            QueueMsg queueMsg = queue.poll();

            try {
                GeneralMsg msg = msgReader.readValue(queueMsg.getMsg());
                handlerRepository.messageHandler(msg).handle(msg, queueMsg.getSession());
            } catch (JsonProcessingException e) {
                log.error(serverInfo.getServerId() + " error occurred", e);
            } catch (HandlerNotRegisteredException e) {
                log.error(serverInfo.getServerId() + " - " + queueMsg.getSession().getId(), e);
            }
        }

        gameInfo.getWorld().step(deltaTime(), 6, 2);

        WorldInfoDTO worldInfo = new WorldInfoDTO(
                gameInfo.getPlayers().entrySet().stream()
                        .map(p -> new PlayerDTO(p.getKey(), p.getValue().getPosition()))
                        .toList()
        );

        try {
            String json = mapper.writeValueAsString(worldInfo);
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

    private Fixture createBackgroundFixture(World world) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(WIDTH/2f, HEIGHT/2f);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH/2f, HEIGHT/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.isSensor = true;

        Fixture fixture = groundBody.createFixture(fixtureDef);
        shape.dispose();

        return fixture;
    }
}
