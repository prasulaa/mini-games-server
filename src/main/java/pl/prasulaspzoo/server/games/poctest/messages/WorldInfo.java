package pl.prasulaspzoo.server.games.poctest.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

import java.util.List;

@AllArgsConstructor
@Getter
public class WorldInfo extends GeneralMsg {
    public static final String NAME = "worldInfo";

    private final List<PlayerDTO> players;

}
