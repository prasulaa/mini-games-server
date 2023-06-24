package pl.prasulaspzoo.server.games.poctest.messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

@NoArgsConstructor
@Setter
@Getter
public class ConnectionRequest extends GeneralMsg {
    public static final String NAME = "connectionRequest";

    private String uid;

}
