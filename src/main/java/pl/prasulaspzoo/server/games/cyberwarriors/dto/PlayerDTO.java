package pl.prasulaspzoo.server.games.cyberwarriors.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

@NoArgsConstructor
@Setter
@Getter
public class PlayerDTO extends GeneralMsg {
    public static final String NAME = "cw-player";

    private String id;
    private float x;
    private float y;

}
