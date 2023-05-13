//package pl.prasulaspzoo.server;
//
//import com.badlogic.gdx.physics.box2d.*;
//import pl.prasulaspzoo.server.manager.ServerInfo;
//
//import java.util.List;
//
//import static pl.prasulaspzoo.server.SpaceObject.Type.*;
//
//public class CollisionListener implements ContactListener {
//
//    private final World world;
//    private final ServerInfo gameState;
//
//    public CollisionListener(World world, ServerInfo gameState) {
//        this.world = world;
//        this.gameState = gameState;
//    }
//
//    @Override
//    public void beginContact(Contact contact) {
//        // SPACE_LASER + ALIEN
//        final Fixture a = contact.getFixtureA().getUserData() == SPACE_LASER ? contact.getFixtureA() : contact.getFixtureB();
//        final Fixture b = contact.getFixtureB().getUserData() == ALIEN ? contact.getFixtureB() : contact.getFixtureA();
//
//        if (a.getUserData() == SPACE_LASER && b.getUserData() == ALIEN) {
////            Gdx.app.postRunnable(new Runnable() {
////                @Override
////                public void run() {
////                    deleteFromList(a, gameState.getSpaceLasers());
////                    deleteFromList(b, gameState.getAliens());
////                    world.destroyBody(a.getBody());
////                    world.destroyBody(b.getBody());
////                    gameState.setScore(gameState.getScore() + 1);
////                }
////            });
//        }
////
//        final Fixture aa = contact.getFixtureA().getUserData() == BOUNDARY_LEFT || contact.getFixtureA().getUserData() == BOUNDARY_RIGHT
//                ? contact.getFixtureA()
//                : contact.getFixtureB();
//        // BOUNDARY + ALIEN
//        if ((aa.getUserData() == BOUNDARY_LEFT || aa.getUserData() == BOUNDARY_RIGHT) && b.getUserData() == ALIEN) {
////            System.out.println(Thread.currentThread());
////            System.out.println(a.getUserData());
////            System.out.println(b.getUserData());
////            Gdx.app.postRunnable(new Runnable() {
////                @Override
////                public void run() {
//                    for (SpaceObject spaceObject: gameState.getAliens()) {
//                        spaceObject.getFixture().getBody().setLinearVelocity(aa.getUserData() == BOUNDARY_LEFT ? 1f : -1f, 0);
//                    }
////                }
////            });
//        }
//    }
//
//    @Override
//    public void endContact(Contact contact) {
//    }
//
//    @Override
//    public void preSolve(Contact contact, Manifold oldManifold) {
//
//    }
//
//    @Override
//    public void postSolve(Contact contact, ContactImpulse impulse) {
//
//    }
//
//    private void deleteFromList(Fixture objectFixture, List<SpaceObject> spaceObjectList) {
//        for (int i = 0; i < spaceObjectList.size(); i++) {
//            if (objectFixture.equals(spaceObjectList.get(i).getFixture())) {
//                spaceObjectList.remove(i);
//                break;
//            }
//        }
//    }
//}
