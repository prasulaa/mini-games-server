package pl.prasulaspzoo.server.games.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "msgType",
        visible = true
)
@JsonSubTypes({
        // POC_TEST
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.ConnectionRequest.class, name = pl.prasulaspzoo.server.games.poctest.messages.ConnectionRequest.NAME),
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.Disconnect.class, name = pl.prasulaspzoo.server.games.poctest.messages.Disconnect.NAME),
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.WorldInfo.class, name = pl.prasulaspzoo.server.games.poctest.messages.WorldInfo.NAME),
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.PlayerDTO.class, name = pl.prasulaspzoo.server.games.poctest.messages.PlayerDTO.NAME),
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.Move.class, name = pl.prasulaspzoo.server.games.poctest.messages.Move.NAME)
})
@NoArgsConstructor
@Setter
@Getter
public class GeneralMsg {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String msgType;
    protected long timestamp = System.currentTimeMillis();

}
