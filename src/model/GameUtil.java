package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
     * Attribute that initiates the MOVING_OBSTACLE value.
     */
    public static final byte MOVING_OBSTACLE = 1;
    
    /**
     * Attribute that initiates the JUMPING_OBSTACLE value.
     */
    public static final byte JUMPING_OBSTACLE = 2;
    
    
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
    
    /**
     * Attribute that initiates the LEVEL_MOVE_MULTIPLAYER value.
     */
    public static final float LEVEL_MOVE_MULTIPLAYER = 5;
    
    /**
     * Attribute that initiates the MAX_DISTANCE_FOR_JUMPING_OBSTACLE value.
     */
    public static final float MAX_DISTANCE_FOR_JUMPING_OBSTACLE = -70;
    
    /**
     * Attribute that initiates the MOVING_SPEED_FOR_JUMPING_OBSTACLE value.
     */
    public static final float MOVING_SPEED_FOR_JUMPING_OBSTACLE = 5;
    
    /**
     * Attribute that initiates the MAIN_STATE value.
     */
    public static final int MAIN_STATE = 0;
    
    /**
     * Attribute that initiates the RUN_STATE value.
     */
    public static final int RUN_STATE = 1;
    
    /**
     * Attribute that initiates the PAUSE_STATE value.
     */
    public static final int PAUSE_STATE = 2;
    
    /**
     * Attribute that initiates the RES_STATE value.
     */
    public static final int RES_STATE = 3;
    
    /**
     * Attribute that initiates the QUIT_STATE value.
     */
    public static final int QUIT_STATE = 4;
    
    /**
     * Attribute that represents current state and next state.
     */
    private static int currentState, nextState;
    
    /**
     * Attribute that represents current level.
     */
    private int currentLevel;
    
    /** Constructor in GameUtil class.
     * 
     */
    private GameUtil() {
        this.currentLevel = 1;
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
    
    /** Method to get Integer random value.
     * 
     * This method returns a random value from the start to end range with the 
     * Integer value.
     * This method will also randomize seed parameter.
     * 
     * @param start
     * @param end
     * @return
     */
    public int randomInt(int start, int end, int seed) {
        return (new Random(seed)).nextInt(end + 1) + start;
    }

    /** Method to get Byte random value.
     * 
     * This method returns a random value from the start to end range with the 
     * Byte value.
     * This method will also randomize seed parameter.
     * 
     * @param start
     * @param end
     * @return 
     */
    public byte randomByte(byte start, byte end, int seed) {
        return (byte) ((new Random(seed)).nextInt(end + 1));
    }

    /** Method to get Float random value.
     * 
     * This method returns a random value from the start to end range with the 
     * Float value.
     * This method will also randomize seed parameter.
     * 
     * @param start
     * @param end
     * @return 
     */
    public float randomFloat(float start, float end, int seed) {
        return (new Random(seed)).nextFloat() * (end-start) + start;
    }

    /** Getter to get current level of the game
     * 
     * @return currentLevel
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /** Setter to set current level of the game
     * 
     * @return
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    
    /** Method to save in-game high score.
     * 
     * This method will write the high score in highscore.txt.
     * This method will throw to IOException.
     * 
     * @return 
     */
    public void saveHighScore(int highScore) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("highscore.txt")));
        bw.write(highScore + "");
        bw.flush();
        bw.close();
    }
    
    /** Method to load in-game high score.
     * 
     * This method will read the high score in highscore.txt.
     * This method will throw to FileNotFoundException and IOException.
     * If baca equal to null or baca is empty, this method will return 0.
     * Else, this method will return temp that parsing baca to integer.
     * 
     * @return 0, temp
     */
    public int loadHighScore() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("highscore.txt")));
        String baca = br.readLine();
        if (baca == null || baca.isEmpty()) {
            saveHighScore(0);
            return 0;
        } else {
            int temp = Integer.parseInt(baca);
            return temp;
        }
    }

    /** Getter to get current state of the game
     * 
     * @return currentState
     */
    public static int getCurrentState() {
        return currentState;
    }

    /** Setter to set current state of the game
     * 
     * @return 
     */
    public static void setCurrentState(int currentState) {
        GameUtil.currentState = currentState;
    }

    /** Getter to get next state of the game
     * 
     * @return nextState
     */
    public static int getNextState() {
        return nextState;
    }

    /** Setter to set next state of the game
     * 
     * @return
     */
    public static void setNextState(int nextState) {
        GameUtil.nextState = nextState;
    }
    
    /** Setter to set next level of the game
     * 
     * @return
     */
    public void setLevel(int level) {
        this.currentLevel = level;
    }
    
}
