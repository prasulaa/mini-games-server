//package pl.prasulaspzoo.server;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Fixture;
//
//public class SpaceShip {
//
//    private final Sprite staticSprite;
//    private final Animation<TextureRegion> dynamicAnimation;
//    private final Fixture fixture;
//
//    public SpaceShip(Texture staticTexture, String animationFileName, Fixture fixture) {
//        this.staticSprite = null;//new Sprite(staticTexture);
//        this.fixture = fixture;
//        this.dynamicAnimation = null; //loadAnimation(animationFileName);
//    }
//
//    private Animation<TextureRegion> loadAnimation(String fileName) {
//        Texture animation = new Texture(Gdx.files.internal(fileName));
//
//        TextureRegion[] frames = TextureRegion.split(animation, animation.getHeight(), animation.getHeight())[0];
//        System.out.println(frames.length);
//        return new Animation<>(0.125f, frames);
//    }
//
//    public Sprite getSprite(float stateTime) {
//        return staticSprite;
////        Vector2 velocity = fixture.getBody().getLinearVelocity();
////
////        if (velocity.x*velocity.x + velocity.y * velocity.y > 0) {
////            return new Sprite(dynamicAnimation.getKeyFrame(stateTime, true));
////        } else {
////            return staticSprite;
////        }
//    }
//
//    public Fixture getFixture() {
//        return fixture;
//    }
//}
