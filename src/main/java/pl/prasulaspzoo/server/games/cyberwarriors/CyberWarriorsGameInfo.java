package pl.prasulaspzoo.server.games.cyberwarriors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.prasulaspzoo.server.games.cyberwarriors.model.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CyberWarriorsGameInfo {

    private final World world;
    private final Fixture backgroundFixture;

    private final Map<String, Player> players = new HashMap<>();

}
