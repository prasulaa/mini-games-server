package pl.prasulaspzoo.server.games.common.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

@NoArgsConstructor
@Setter
@Getter
public class Disconnect extends GeneralMsg {
    public static final String NAME = "disconnect";

    private String uid;

}
