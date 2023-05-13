package pl.prasulaspzoo.server.games.poctest.messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ConnectionRequest extends GeneralMsg {
    public static final String NAME = "connectionRequest";

    private String uid;

}
