package pl.prasulaspzoo.server.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.prasulaspzoo.server.games.Game;
import pl.prasulaspzoo.server.games.GameFactory;
import pl.prasulaspzoo.server.games.GameType;
import pl.prasulaspzoo.server.queue.QueueDispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GameManager {

    @Value("${servers.tickrate:64}")
    private int tickrate;
    private final ScheduledExecutorService executor;
    private final QueueDispatcher queueDispatcher;
    private final List<Game> games;

    public ServerInfo createServer(GameType gameType) {
        String serverId = UUID.randomUUID().toString().replace("-", "");
        ServerInfo serverInfo = new ServerInfo(serverId, queueDispatcher.createGameQueue(serverId), new HashMap<>(), new ConcurrentHashMap<>(), tickrate);

        Game game = GameFactory.createGame(serverInfo, queueDispatcher, gameType);
        games.add(game);
        ScheduledFuture<?> scheduledTask = executor.scheduleAtFixedRate(game, 0, 1000/tickrate, TimeUnit.MILLISECONDS);
        game.setScheduledTask(scheduledTask);

        return serverInfo;
    }

    public Game getGame(String serverId) {
        return games.stream()
                .filter(game -> game.getServerInfo().getServerId().equals(serverId))
                .findAny()
                .orElse(null);
    }


}
