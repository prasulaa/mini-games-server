package pl.prasulaspzoo.server.games.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.prasulaspzoo.server.games.common.message.ConnectionRequest;
import pl.prasulaspzoo.server.games.common.message.Disconnect;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerDTO;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "msgType",
        visible = true
)
@JsonSubTypes({
        // COMMON
        @JsonSubTypes.Type(value = ConnectionRequest.class, name = ConnectionRequest.NAME),
        @JsonSubTypes.Type(value = Disconnect.class, name = Disconnect.NAME),

        // POC_TEST
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.WorldInfo.class, name = pl.prasulaspzoo.server.games.poctest.messages.WorldInfo.NAME),
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.PlayerDTO.class, name = pl.prasulaspzoo.server.games.poctest.messages.PlayerDTO.NAME),
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.poctest.messages.Move.class, name = pl.prasulaspzoo.server.games.poctest.messages.Move.NAME),

        // CYBER WARRIORS 2115
        @JsonSubTypes.Type(value = pl.prasulaspzoo.server.games.cyberwarriors.dto.PlayerDTO.class, name = PlayerDTO.NAME)
})
@NoArgsConstructor
@Setter
@Getter
public class GeneralMsg {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String msgType;
    protected long timestamp = System.currentTimeMillis();

}
