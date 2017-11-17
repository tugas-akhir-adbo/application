package model;

import java.util.Random;

/** This class represents random value that will be implemented to the
 * objects in the game.
 * 
 * @author fakhry dinan idris
 */
public class GameUtil {

    /**
     * Attribute to instantiate GameUtil class.
     */
    private static GameUtil instance;

    /**
     * Attribute that initiates the BOX value.
     */
    public static final byte BOX = 1;
    
    /**
     * Attribute that initiates the CYLINDER value.
     */
    public static final byte CYLINDER = 2;
    
    /**
     * Attribute that initiates the QUAD value.
     */
    public static final byte QUAD = 3;
    
    /**
     * Attribute that initiates the SPHERE value.
     */
    public static final byte SPHERE = 4;
    
    /**
     * Attribute that initiates the SIMPLE_OBSTACLE value.
     */
    public static final byte SIMPLE_OBSTACLE = 0;
    
    /**
     * Attribute that initiates the JUMPING_OBSTACLE value.
     */
    public static final byte JUMPING_OBSTACLE = 1;
    
    /**
     * Attribute that initiates the MOVING_OBSTACLE value.
     */
    public static final byte MOVING_OBSTACLE = 2;
    
    /**
     * Attribute that initiates the FLYING_OBSTACLE value.
     */
    public static final byte FLYING_OBSTACLE = 3;
    
    /**
     * Attribute that initiates the LEFT_LANE value.
     */
    public static final byte LEFT_LANE = 0;
    
    /**
     * Attribute that initiates the CENTER_LANE value.
     */
    public static final byte CENTER_LANE = 1;
    
    /**
     * Attribute that initiates the RIGHT_LANE value.
     */
    public static final byte RIGHT_LANE = 2;
    
    /**
     * Attribute that initiates the ROTATE_FRONT value.
     */
    public static final int ROTATE_FRONT = -3;
    
    /**
     * Attribute that initiates the ROTATE_SIDE value.
     */
    public static final int ROTATE_SIDE = 20;
    
    /**
     * Attribute that initiates the V_TERRAIN_MOVE value.
     */
    public static final float V_TERRAIN_MOVE = 100;
    
    /**
     * Attribute that initiates the ACCELERATOR_CHANGE_LANE value.
     */
    public static final float ACCELERATOR_CHANGE_LANE = 100;
    
    /**
     * Attribute that initiates the V_CHANGE_LANE value.
     */
    public static final float V_CHANGE_LANE = 100;
    
    /**
     * Attribute that initiates the CHANGE_LANE_DISTANCE value.
     */
    public static final float CHANGE_LANE_DISTANCE = 128f / 3f;
    
    /**
     * Attribute that initiates the MAX_MAP value.
     */
    public static final float MAX_MAP = 1024f;

    
    /** Constructor in GameUtil class.
     * 
     */
    private GameUtil() {
    }

    /** Method to instantiate GameUtil class.
     * 
     * As long as the value of the instance is null, this method will
     * instantiate GameUtil class.
     * 
     * @return instance
     */
    public static GameUtil getInstance() {
        if (instance == null) {
            instance = new GameUtil();
        }

        return instance;
    }

    /** Method to get Integer random value.
     * 
     * This method returns a random value from the start to end range with the 
     * Integer value.
     * 
     * @param start
     * @param end
     * @return
     */
    public int randomInt(int start, int end) {
        return (new Random()).nextInt(end + 1) + start;
    }

    /** Method to get Byte random value.
     * 
     * This method returns a random value from the start to end range with the 
     * Byte value.
     * 
     * @param start
     * @param end
     * @return 
     */
    public byte randomByte(byte start, byte end) {
        return (byte) ((new Random()).nextInt(end + 1));
    }

    
    /** Method to get Float random value.
     * 
     * This method returns a random value from the start to end range with the 
     * Float value.
     * 
     * @param start
     * @param end
     * @return 
     */
    public float randomFloat(float start, float end) {
        return (new Random()).nextFloat() * (end - start) + start;
    }
}
