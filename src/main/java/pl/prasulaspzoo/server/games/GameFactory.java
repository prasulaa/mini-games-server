package pl.prasulaspzoo.server.games;

import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.games.poctest.PoCTestGame;
import pl.prasulaspzoo.server.queue.QueueDispatcher;

public class GameFactory {

    public static Game createGame(ServerInfo serverInfo, QueueDispatcher queueDispatcher, GameType gameType) {
        if (gameType == GameType.POC_TEST) {
           PoCTestGame game = new PoCTestGame(serverInfo, queueDispatcher);
           game.init();
           return game;
        } else {
            throw new GameNotFoundException(gameType + " not found");
        }
    }

}
