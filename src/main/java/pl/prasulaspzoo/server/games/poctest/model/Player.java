package pl.prasulaspzoo.server.games.poctest.model;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Player {

    private final String id;
    private final Fixture fixture;
    private final FrictionJoint frictionJoint;

}
