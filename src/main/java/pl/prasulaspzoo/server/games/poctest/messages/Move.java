package pl.prasulaspzoo.server.games.poctest.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Move extends GeneralMsg {

    public static final String NAME = "move";

    private float x;
    private float y;

}
