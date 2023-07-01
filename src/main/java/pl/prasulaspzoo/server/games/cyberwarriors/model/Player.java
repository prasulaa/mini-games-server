package pl.prasulaspzoo.server.games.cyberwarriors.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.utils.Disposable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Player implements Disposable {

    @Getter
    private final Fixture fixture;
    private final FrictionJoint frictionJoint;
    private final World world;

    @Override
    public void dispose() {
        world.destroyJoint(frictionJoint);
        world.destroyBody(fixture.getBody());
    }

    public Vector2 getPosition() {
        return fixture.getBody().getPosition();
    }

}
