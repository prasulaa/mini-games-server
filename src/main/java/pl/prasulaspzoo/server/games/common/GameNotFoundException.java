package pl.prasulaspzoo.server.games.common;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String message) {
        super(message);
    }

}
