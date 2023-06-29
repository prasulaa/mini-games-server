package pl.prasulaspzoo.server.games.cyberwarriors.model;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Player {

    private final Fixture fixture;
    private final FrictionJoint frictionJoint;

}
