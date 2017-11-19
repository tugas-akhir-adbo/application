/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
    public static final byte MOVING_OBSTACLE = 1;
    public static final byte JUMPING_OBSTACLE = 2;
    public static final byte FLYING_OBSTACLE = 3;
    public static final byte LEFT_LANE = 0;
    public static final byte CENTER_LANE = 1;
    public static final byte RIGHT_LANE = 2;
    public static final int ROTATE_FRONT = -3;
    public static final int ROTATE_SIDE = 20;
    public static final float V_TERRAIN_MOVE = 100;
    public static final float ACCELERATOR_CHANGE_LANE = 100;
    public static final float V_CHANGE_LANE = 100;
    public static final float CHANGE_LANE_DISTANCE = 128f / 3f;
    public static final float MAX_MAP = 1024f;
    public static final float LEVEL_MOVE_MULTIPLAYER = 5;
    public static final float MAX_DISTANCE_FOR_JUMPING_OBSTACLE = -70;
    public static final float MOVING_SPEED_FOR_JUMPING_OBSTACLE = 5;
    
    public static final int MAIN_STATE = 0;
    public static final int RUN_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int RES_STATE = 3;
    public static final int QUIT_STATE = 4;
    
    private static int currentState, nextState;
    
    private static int currentLevel;

    private GameUtil() {
        GameUtil.currentLevel = 1;
    }

    public static GameUtil getInstance() {
        if (instance == null) {
            instance = new GameUtil();
        }

        return instance;
    }

    public int randomInt(int start, int end) {
        return (new Random()).nextInt(end + 1) + start;
    }
    
    /**
     *
     * @param start
     * @param end
     * @return
     */
    public int randomInt(int start, int end, int seed) {
        return (new Random(seed)).nextInt(end + 1) + start;
    }

    public byte randomByte(byte start, byte end, int seed) {
        return (byte) ((new Random(seed)).nextInt(end + 1));
    }

    public float randomFloat(float start, float end, int seed) {
        return (new Random(seed)).nextFloat() * (end-start) + start;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        GameUtil.currentLevel = currentLevel;
    }
    
    public void saveHighScore(int highScore) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("highscore.txt")));
        bw.write(highScore + "");
        bw.flush();
        bw.close();
    }
    
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

    public static int getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(int currentState) {
        GameUtil.currentState = currentState;
    }

    public static int getNextState() {
        return nextState;
    }

    public static void setNextState(int nextState) {
        GameUtil.nextState = nextState;
    }
    
    public void setLevel(int level) {
        GameUtil.currentLevel = level;
    }
    
}
