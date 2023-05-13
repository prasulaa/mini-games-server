//package pl.prasulaspzoo.server;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.physics.box2d.Fixture;
//import com.badlogic.gdx.utils.Disposable;
//import org.w3c.dom.Text;
//
//public class SpaceObject implements Disposable {
//
//    private final Texture texture;
//    private final Fixture fixture;
//
//    public SpaceObject(Texture texture, Fixture fixture) {
//        this.texture = texture;
//        this.fixture = fixture;
//    }
//
//    public Texture getTexture() {
//        return texture;
//    }
//
//    public Fixture getFixture() {
//        return fixture;
//    }
//
//    @Override
//    public void dispose() {
//        texture.dispose();
//    }
//
//    public enum Type {
//
//        SPACE_SHIP, SPACE_LASER, ALIEN,
//        BOUNDARY_LEFT, BOUNDARY_RIGHT
//
//    }
//
//}
