package pl.prasulaspzoo.server.games.common;

import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGame;
import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.games.poctest.PoCTestGame;
import pl.prasulaspzoo.server.queue.QueueDispatcher;

public class GameFactory {

    public static Game createGame(ServerInfo serverInfo, QueueDispatcher queueDispatcher, GameType gameType) {
        Game game = switch (gameType) {
            case POC_TEST -> new PoCTestGame(serverInfo, queueDispatcher);
            case CYBER_WARRIORS -> new CyberWarriorsGame(serverInfo, queueDispatcher);
            default -> throw new GameNotFoundException(gameType + " not found");
        };

        game.init();
        return game;
    }

}
