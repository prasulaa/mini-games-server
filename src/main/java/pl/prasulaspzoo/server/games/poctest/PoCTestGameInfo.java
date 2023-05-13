package pl.prasulaspzoo.server.games.poctest;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.prasulaspzoo.server.games.poctest.model.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class PoCTestGameInfo {

    private final World world;
    private final Map<String, Player> players = new HashMap<>();
    private final Fixture ground;

}
