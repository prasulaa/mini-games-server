package pl.prasulaspzoo.server.games.cyberwarriors.dto;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.prasulaspzoo.server.games.common.GeneralMsg;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class PlayerDTO extends GeneralMsg {
    public static final String NAME = "cw-player";

    private String id;
    private float x;
    private float y;

    public PlayerDTO(String id, Vector2 pos) {
        this.id = id;
        this.x = pos.x;
        this.y = pos.y;
    }

}
