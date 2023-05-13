//package pl.prasulaspzoo.server;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.utils.Array;
//import jakarta.annotation.PostConstruct;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pl.prasulaspzoo.server.manager.ServerInfo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class Controller {
//
//    private static final int WIDTH = 800;
//    private static final int HEIGHT = 480;
//    private static final int PPM = 64;
//    private static final float IMPULSE = 0.5f;
//    private static final float MAX_VELOCITY = 3f;
//    private static final long SHOOT_DELAY = 500L;
//    private static final long START_TIME = System.currentTimeMillis();
//
//    private World world;
//    private ServerInfo gameState;
//
//    @PostConstruct
//    private void init() {
//        world = new World(new Vector2(0, 0), true);
//
//        List<SpaceObject> aliens = new ArrayList<>();
//        boolean movLeft = Math.random() < 0.5;
//        for (int i = 0; i < 50; i++) {
//            aliens.add(createAlien(i, movLeft));
//        }
//        List<SpaceObject> lasers = new ArrayList<>();
//        gameState = new ServerInfo(createSpaceShip(), lasers, aliens, 0);
//        createBoundary(true);
//        createBoundary(false);
//
//        world.setContactListener(new CollisionListener(world, gameState));
//    }
//
//    @GetMapping("/step")
//    public void doStep() {
//        doPhysicsStep(0.016f);
//
//        Array<Body> bodies = new Array<>();
//        world.getBodies(bodies);
//        System.out.println(bodies.get(0).getPosition());
//        System.out.println(bodies.get(1).getPosition());
//        System.out.println(bodies.get(2).getPosition());
//    }
//
//    public void doPhysicsStep(float deltaTime) {
//        float dt = 1/60f;
//
//        while (deltaTime > 0.0) {
//            float timeStep = Math.min(deltaTime, dt);
//            world.step(timeStep, 6, 2);
//            deltaTime -= timeStep;
//        }
//    }
//
//    private SpaceObject createAlien(int i, boolean movLeft) {
//        Abc texture = new Abc(64, 64);//new Texture("alien.png");
//
//        int maxInRow = WIDTH/texture.getWidth() - 2;
//        float x = (i % maxInRow + 1.5f) * texture.getWidth();
//        float y = HEIGHT - (i / maxInRow + 0.5f) * texture.getHeight();
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(x/PPM, y/PPM);
//        bodyDef.fixedRotation = true;
//
//        Body body = world.createBody(bodyDef);
//        body.setLinearVelocity(movLeft ? -1f : 1f, 0);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(texture.getWidth()/2f/PPM, texture.getHeight()/2f/PPM);
//        Fixture fixture = body.createFixture(shape, 1f);
//        fixture.setUserData(SpaceObject.Type.ALIEN);
//        fixture.setSensor(true);
//
//        shape.dispose();
//
//        return new SpaceObject(null, fixture);
//    }
//
//    private SpaceObject createSpaceLaser() {
//        Abc texture = new Abc(64, 64);//new Texture("spacelaser.png");
//
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        Vector2 shipPos = gameState.getSpaceShip().getFixture().getBody().getPosition();
//        bodyDef.position.set(shipPos.x, shipPos.y + (gameState.getSpaceShip().getSprite(getStateTime()).getHeight() + texture.getHeight())/2f/PPM);
//        bodyDef.fixedRotation = true;
//
//        Body body = world.createBody(bodyDef);
//        body.setLinearVelocity(0, 3f);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(texture.getWidth()/2f/PPM, texture.getHeight()/2f/PPM);
//        Fixture fixture = body.createFixture(shape, 1f);
//        fixture.setUserData(SpaceObject.Type.SPACE_LASER);
//        shape.dispose();
//
//        return new SpaceObject(null, fixture);
//    }
//
//    private float getStateTime() {
//        return (System.currentTimeMillis() - START_TIME) / 1000f;
//    }
//
//    private SpaceShip createSpaceShip() {
//        Abc texture = new Abc(64, 64);//new Texture("spaceship.png");
//
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set((WIDTH - texture.getWidth())/2f/PPM, (20f + texture.getHeight()/2f)/PPM);
//        bodyDef.fixedRotation = true;
//
//        Body body = world.createBody(bodyDef);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(texture.getWidth()/2f/PPM, texture.getHeight()/2f/PPM);
//        Fixture fixture = body.createFixture(shape, 1f);
//        fixture.setUserData(SpaceObject.Type.SPACE_SHIP);
//        shape.dispose();
//
//        return new SpaceShip(null, "spaceship_mov.png", fixture);
//    }
//
//    private Fixture createBoundary(boolean left) {
//        int boundaryWidth = 10;
//
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(left ? -boundaryWidth/2f/PPM : (WIDTH+boundaryWidth/2f)/PPM, HEIGHT/2f/PPM);
//
//        Body body = world.createBody(bodyDef);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(boundaryWidth/2f/PPM, HEIGHT/2f/PPM);
//        Fixture fixture = body.createFixture(shape, 1f);
//        fixture.setUserData(left ? SpaceObject.Type.BOUNDARY_LEFT : SpaceObject.Type.BOUNDARY_RIGHT);
//        shape.dispose();
//
//        return fixture;
//    }
//
//    private class Abc {
//        int w, h;
//
//        public Abc(int w, int h) {
//            this.w = w;
//            this.h = h;
//        }
//
//        public int getWidth() {
//            return w;
//        }
//
//        public int getHeight() {
//            return h;
//        }
//    }
//
//}
