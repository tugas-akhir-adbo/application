package model;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class is a factory class to produce obstacle in this game.
 * This is a singleton class; calling getInstance will get this object
 * 
 * @author Joshua Lauwrich
 * @since Tuesday, 14 November 2017
 * @version 3.1
 */
public class ObstacleFactory {

    private static ObstacleFactory instance;                //The instance of this class
    private final LinkedList<Obstacle>[] obstaclePool;      //Pool to store all generated obstacle
    private final LinkedList<Obstacle> onScreenObstacle;    //List of current obstacle in the screen
    private AssetManager am;                                //Asset manager for loading assets               
    private Node rootNode;                                  //Root node for attaching the obstacle
    private long currentCounter;                            //Counter for how long time passed since the last call
    private byte targetSpawnTimer;                          //Time for obstacle to be spawned

    /**
     * Get this class instance.
     * Calling this will return this class object because this is a singleton class.
     * 
     * @return this class object
     */
    public static ObstacleFactory getInstance() {
        if (instance == null) {
            instance = new ObstacleFactory();
        }

        return instance;
    }

    //Private constructor for this class
    private ObstacleFactory() {
        this.obstaclePool = new LinkedList[]{new LinkedList(), new LinkedList(), new LinkedList(), new LinkedList()};
        this.onScreenObstacle = new LinkedList<>();
        this.targetSpawnTimer = GameUtil.getInstance().randomByte((byte) 0, (byte) 5, GameUtil.getInstance().randomInt(0, 100000));
        this.currentCounter = System.currentTimeMillis();
    }

    /**
     * Method for spawning obstacle depends on its type and lane.
     * This method is called randomly depends on current level of the game.
     * 
     * @param type the type of the obstacle
     * @param lane the lane for this obstacle to be spawned (left, middle, right)
     */
    private void spawnObstacle(byte type, byte lane) {
        
        //If the pool is empty, automatically create one and added it to the pool
        if (obstaclePool[type].isEmpty()) {
            Obstacle temp = generateObstacle(type, lane);
            obstaclePool[type].add(temp);
            setSpawnPoint(temp, lane);
            return;
        }

        //Iterate over the pool to find the obstacle that is free and not in the screen
        Iterator<Obstacle> curIt = obstaclePool[type].iterator();
        while (curIt.hasNext()) {
            Obstacle temp = curIt.next();
            
            //If the obstacle not in the screen, set the spawn location
            if (!temp.isOnScreen()) {
                setSpawnPoint(temp, lane);
                return;
            }
        }

        //if none of the above conditions are met, generate a new obstacle, add it to the pool, and spawn it
        Obstacle temp = generateObstacle(type, lane);
        obstaclePool[type].add(temp);
        setSpawnPoint(temp, lane);
    }

    /**
     * Set the obstacle spawn point to the right position depends on its lane.
     * 
     * @param obstacle The obstacle object to be spawned
     * @param lane Lane where the object is placed
     */
    private void setSpawnPoint(Obstacle obstacle, byte lane) {
        
        //Check if it was instance of ChangingLaneObstacle because the method call is different
        if (obstacle instanceof ChangingLaneObstacle) {
            ((ChangingLaneObstacle) obstacle).changeLane(lane);
        } else {
            obstacle.setX(lane == GameUtil.LEFT_LANE ? GameUtil.CHANGE_LANE_DISTANCE * -1
                    : (lane == GameUtil.CENTER_LANE ? 0 : GameUtil.CHANGE_LANE_DISTANCE));
        }
        //Set the z position to the end of the map
        obstacle.setZ(GameUtil.MAX_MAP * -1);
        
        //Attach the obstacle to the root node
        rootNode.attachChild(obstacle.getSpatial());
        
        //Add to the list of current obstacle
        onScreenObstacle.add(obstacle);
        
        //Set the variable onScreen to true
        obstacle.setOnScreen(true);
    }

    /**
     * Generate/make a new obstacle object depends on its type and lane
     * 
     * @param type the type of the obstacle
     * @param lane the lane for this obstacle to be spawned (left, middle, right)
     * @return a new obstacle object depends on its type
     */
    private Obstacle generateObstacle(byte type, byte lane) {
        switch (type) {
            case GameUtil.SIMPLE_OBSTACLE:
                float tinggi = GameUtil.getInstance().randomInt(0, 50) / 2;
                Material mat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
                Texture tex = am.loadTexture("Materials/1.jpg");
                return new ConcreteObstacle(0, tinggi, 0, 0, 0, GameUtil.V_TERRAIN_MOVE, 15, tinggi * 2, 15,
                        GameUtil.CYLINDER, true, "SimpleObstacle" + obstaclePool[type].size(), am, mat, tex);

            case GameUtil.JUMPING_OBSTACLE:
                mat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
                tex = am.loadTexture("Materials/1.jpg");
                return new JumpingObstacle(new ConcreteObstacle(0, GameUtil.MAX_DISTANCE_FOR_JUMPING_OBSTACLE, 0, 0, GameUtil.MOVING_SPEED_FOR_JUMPING_OBSTACLE, GameUtil.V_TERRAIN_MOVE, 0, 0, 15,
                        GameUtil.SPHERE, true, "Jumping Obstacle " + obstaclePool[type].size(), am, mat, tex));

            case GameUtil.MOVING_OBSTACLE:
                mat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
                tex = am.loadTexture("Materials/1.jpg");
                return new ChangingLaneObstacle(new ConcreteObstacle(0, GameUtil.getInstance().randomFloat(5, 30, GameUtil.getInstance().randomInt(0, 100000)), 0, 50, 0, GameUtil.V_TERRAIN_MOVE, 0, 0, 10,
                        GameUtil.SPHERE, true, "Moving Obstacle " + obstaclePool[type].size(), am, mat, tex));
        }
        return null;

    }

    /**
     * Initialize this factory with the information.
     * 
     * @param am Asset manager for loading assets
     * @param rootNode Root node for attaching the obstacle
     */
    public void initFactory(AssetManager am, Node rootNode) {
        this.am = am;
        this.rootNode = rootNode;
    }

    /**
     * Updating this factory includes all obstacles on it.
     * @param tpf time per frame
     */
    public void update(float tpf) {
        long temp = System.currentTimeMillis();
        
        //Spawn the obstacle if passed the target spawn time
        if (temp - currentCounter >= targetSpawnTimer * 1000) {
            currentCounter = temp;
            byte randomJenis;
            
            //Generate random obstacle; the type depends on current level of the game
            if (GameUtil.getInstance().getCurrentLevel() <= 5) randomJenis = 0;
            else if (GameUtil.getInstance().getCurrentLevel() <= 10) randomJenis = GameUtil.getInstance().randomByte((byte)0, (byte)1, GameUtil.getInstance().randomInt(0, 100000));
            else randomJenis = GameUtil.getInstance().randomByte((byte)0, (byte)2, GameUtil.getInstance().randomInt(0, 100000));
            spawnObstacle(randomJenis, GameUtil.getInstance().randomByte((byte) 0, (byte) 2, GameUtil.getInstance().randomInt(0, 1000000)));
            
            //Generate new target spawn time depends on current level of the game
            if (GameUtil.getInstance().getCurrentLevel() <= 5) targetSpawnTimer = GameUtil.getInstance().randomByte((byte) 0, (byte) 6, GameUtil.getInstance().randomInt(0, 100000));
            else if (GameUtil.getInstance().getCurrentLevel() <= 10) targetSpawnTimer = GameUtil.getInstance().randomByte((byte) 0, (byte) 4, GameUtil.getInstance().randomInt(0, 100000));
            else targetSpawnTimer = GameUtil.getInstance().randomByte((byte) 0, (byte) 2, GameUtil.getInstance().randomInt(0, 100000));

        }
        
        //Iterate over all the obstacle on the screen to update it position and condition
        Iterator<Obstacle> currIt = onScreenObstacle.iterator();
        while (currIt.hasNext()) {
            Obstacle next = currIt.next();
            //If the obstacle has reach the end, detach it from the node, remove it from the list, and set on screen to false
            if (next.getZ() > 200) {
                next.setOnScreen(false);
                rootNode.detachChild(next.getSpatial());
                currIt.remove();
            }
            next.update(tpf);
        }
    }

    /**
     * Check whether the player collide the obstacle or not
     * 
     * @param player Player object to be checked
     * @return true if the player collide an obstacle
     */
    public boolean isColliding(Player player) {
        Iterator<Obstacle> it = onScreenObstacle.iterator();
        while (it.hasNext()) {
            CollisionResults res = new CollisionResults();
            Obstacle temp = it.next();
            
            //Check the collision and save it to the res variable
            player.getSpatial().collideWith(temp.getSpatial().getWorldBound(), res);
            
            //Check if the size of collision more that the limit depends on its type
            if (temp instanceof ChangingLaneObstacle) {
                if (res.size() >= 5) {
                    return true;
                }
            } else if (temp instanceof JumpingObstacle) {
                if (res.size() >= 6) {
                    return true;
                }
            } else {
                if (res.size() > 7) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Leveled up all the obstacle in the pool
     */
    public void levelUp() {
        for (LinkedList ll : obstaclePool) {
            Iterator<Obstacle> it = ll.iterator();
            while (it.hasNext()) {
                it.next().levelUp();
            }
        }
    }

    /**
     * Restarting all the obstacle state in the pool, detach all obstacle from the node, and clean the list
     */
    public void restartGame() {
        Iterator<Obstacle> it = onScreenObstacle.iterator();
        while (it.hasNext()) {
            Obstacle next = it.next();
            
            //Remove from root node and clear the list
            next.setOnScreen(false);
            rootNode.detachChild(next.getSpatial());
            it.remove();
        }
        
        //Restarting all obstacle state in the pool
        for (LinkedList ll : obstaclePool) {
            Iterator<Obstacle> it2 = ll.iterator();
            while (it2.hasNext()) {
                it2.next().restartLevel();
            }
        }
    }
}
