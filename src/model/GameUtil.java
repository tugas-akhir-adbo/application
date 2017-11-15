/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author i15019
 */
public class GameUtil {

    private static GameUtil instance;
    public static final byte BOX = 1;
    public static final byte CYLINDER = 2;
    public static final byte QUAD = 3;
    public static final byte SPHERE = 4;
    public static final byte SIMPLE_OBSTACLE = 0;
    public static final byte JUMPING_OBSTACLE = 1;
    public static final byte MOVING_OBSTACLE = 2;
    public static final byte LEFT_LANE = 0;
    public static final byte CENTER_LANE = 1;
    public static final byte RIGHT_LANE = 2;
    public static final int ROTATE_FRONT = -3;
    public static final int ROTATE_SIDE = 5;
    public static final float V_TERRAIN_MOVE = 100;
    public static final float ACCELERATOR_CHANGE_LANE = 100;
    public static final float V_CHANGE_LANE = 150;
    public static final float CHANGE_LANE_DISTANCE = 128f/3f;

    private GameUtil() {
    }

    public GameUtil getInstance() {
        if (instance == null) {
            instance = new GameUtil();
        }
        
        return instance;
    }

    public int randomInt(int start, int end) {
        int random = (int) (Math.random() * end + start);
        return random;
    }
    
    public byte randomByte(byte start, byte end) {
        byte random = (byte) (Math.random() * end + start);
        return random;
    }
    
    public float randomFloat(float start, float end) {
        float random = (float) (Math.random() * end + start);
        return random;
    }
}
