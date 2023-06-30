package pl.prasulaspzoo.server.games.cyberwarriors.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class WorldInfoDTO extends GeneralMsg {
    public static final String NAME = "cw-worldinfo";

    private final List<PlayerDTO> players;

}
