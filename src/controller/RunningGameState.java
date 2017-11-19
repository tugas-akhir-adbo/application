package controller;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.texture.Texture;
import java.io.IOException;
import main.Main;
import model.GameUtil;
import model.ObstacleFactory;
import model.Player;
import model.TerrainFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 * This is Run State Class that appear when the game is running. This class
 * handle the state transition when pausing, restarting, or ending the game.
 *
 * @author i16072 - Joshua Lauwrich N
 * @since Monday, 06 November 2017
 * @version 3.5
 */
public class RunningGameState extends BaseAppState implements ScreenController {

    private Main app;                           //The app instance
    private Node rootNode;                      //The root node
    private AssetManager assetManager;          //Asset manager
    private InputManager inputManager;          //Input manager
    private Node playerNode;                    //Node for the player
    private Player player;                      //Player object
    private TerrainFactory terrainFactory;      //Factory class to produce terrain
    private Spatial particle;                   //Particle object behind the player
    private ObstacleFactory obstacleFactory;    //Factory class to produce obstacle
    private int score, highScore;               //Store current score and high score
    private float tempScore;                    //Temp variable to store how much time passed since the last score update
    private Element scoreLabel;                 //Label from the GUI to show the score
    private MyInputListener inputListener;      //Input listener class to handle input from user
    private byte currentScreen;                 //Show which screen is currently being shown (from GUI)
    private static boolean pause = false;       //True if the game is paused and this state also paused
    private boolean isPlaySoundHighScore;       //True if the sound effect is played
    private AudioNode highScorePassed;          //Node for sound effect when player pass the highscore
    private AudioNode die;                      //Node for sound effect when player hit the obstacle

    @Override
    protected void initialize(Application app) {

        //Initialize the variable
        this.app = (Main) getApplication();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        playerNode = new Node("player node");

        pause = false;
        isPlaySoundHighScore = false;

        //set current screen to run state
        currentScreen = GameUtil.RUN_STATE;

        //Try loading the highscore from txt file
        try {
            this.highScore = GameUtil.getInstance().loadHighScore();
        } catch (IOException ex) {
            this.highScore = 0;
            System.out.println("FAILED TO LOAD HAHAHAHA");
        }

        //Initialize the light for the game
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);

        //Initialize all the object state and input
        initTerrain();
        initCamera();
        initPlayer();
        initKeys();
        initObstacle();
        initAudioEffect();
    }

    /**
     * Get the factory instance and initialize the terrain with the road above
     * it
     */
    private void initTerrain() {
        terrainFactory = TerrainFactory.getInstance();
        for (int i = 1; i <= 4; i++) {
            //Adding the terrain to root node
            terrainFactory.addTerrain("terrain" + i, assetManager, rootNode);
        }
    }

    /**
     * Initialize the player and particle effect behind it
     */
    private void initPlayer() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        Texture playerTexture = assetManager.loadTexture("Materials/neon-square.jpg");
        mat.setTexture("ColorMap", playerTexture);

        //Create the player
        player = new Player(0, 7.5f + 7, 0, 0, 0, 0, 15, 15, 15, GameUtil.BOX, false, "player", assetManager, mat, null);

        //Attach the player
        playerNode.attachChild(player.getSpatial());

        //Create the particle
        particle = assetManager.loadModel("Models/paricles.j3o");

        //Attach the particle
        playerNode.attachChild(particle);

        //Attach the player and particle to root node
        rootNode.attachChild(playerNode);
    }

    /**
     * Get the factory instance and initialize the factory
     */
    private void initObstacle() {
        obstacleFactory = ObstacleFactory.getInstance();
        obstacleFactory.initFactory(assetManager, rootNode);
    }

    /**
     * Initialize all key for input and add the input listener to input manager
     */
    private void initKeys() {
        // Creating an audio node for sound when jumping
        AudioNode jumpNode = new AudioNode(assetManager, "Sounds/jump.ogg", DataType.Buffer);
        jumpNode.setPositional(false);
        jumpNode.setLooping(false);
        jumpNode.setVolume(2);

        //Attach the audio node to root node
        rootNode.attachChild(jumpNode);

        //Create the input listener object
        inputListener = new MyInputListener(player, jumpNode);

        //Add mapping to the listener
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_UP),
                new KeyTrigger(KeyInput.KEY_SPACE));

        //Adding the listener to the input manager with the name for each action
        inputManager.addListener(inputListener, new String[]{"Pause", "Left", "Right", "Jump"});

    }

    /**
     * Create the camera and attach it to the player node
     */
    private void initCamera() {
        //Create the camera node
        CameraNode camNode = new CameraNode("CamNode", this.app.getCamera());

        //Set the direction and the location of the camera
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 53, 165));

        //Attach it to the player node
        playerNode.attachChild(camNode);

        //Make the camera always look at the player
        camNode.lookAt(playerNode.getLocalTranslation(), Vector3f.UNIT_Y);
    }

    private void initAudioEffect() {
        //Initialize the audio
        highScorePassed = new AudioNode(assetManager, "Sounds/passHighScore.ogg", DataType.Buffer);
        highScorePassed.setPositional(false);
        highScorePassed.setLooping(false);
        highScorePassed.setVolume(2);

        die = new AudioNode(assetManager, "Sounds/die.ogg", DataType.Buffer);
        die.setPositional(false);
        die.setLooping(false);
        die.setVolume(2);

        //Attach the audio node to root node
        rootNode.attachChild(highScorePassed);
        rootNode.attachChild(die);
    }

    @Override
    protected void cleanup(Application app) {
        Platform.exit();
    }

    @Override
    protected void onEnable() {
        //Get the label object
        scoreLabel = Main.nifty.getScreen("scoreBoard").findElementById("score");
    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {
        //Update depends on the state of the game
        switch (GameUtil.getCurrentState()) {
            case GameUtil.RUN_STATE:
                //Check if the current screen is the score board
                if (currentScreen != GameUtil.RUN_STATE) {
                    Main.nifty.gotoScreen("scoreBoard");
                    //Change the current screen displayed
                    currentScreen = GameUtil.RUN_STATE;
                }

                //Updating all the object
                player.update(tpf);
                terrainFactory.update(tpf);
                obstacleFactory.update(tpf);

                //Set the position of the particle
                if (player.isJumping()) {
                    particle.setLocalTranslation(player.getX() + 10, player.getY() - 4, player.getZ() - 40);
                } else {
                    particle.setLocalTranslation(player.getX() - 10, player.getY() - 4, player.getZ() - 5);
                }

                //Check if the player collide the obstacle
                if (obstacleFactory.isColliding(player)) {

                    //The game is over, change to result state and pausing the game
                    GameUtil.setNextState(GameUtil.RES_STATE);
                    setPause(true);
                }

                //Set the score for every second has passed
                if (tempScore >= 1) {
                    score++;
                    tempScore = 0 + (tempScore - 1);

                    //Leveled up the game
                    if (score % 5 == 0) {
                        GameUtil.getInstance().setCurrentLevel(GameUtil.getInstance().getCurrentLevel() + 1);
                        terrainFactory.levelUp();
                        obstacleFactory.levelUp();
                    }
                } else {
                    tempScore += tpf;
                }

                //Set the label text with the current score
                scoreLabel.getNiftyControl(Label.class).setText(score + "");

                //Play the sound effect
                if (score > highScore && !isPlaySoundHighScore) {
                    highScorePassed.playInstance();
                    isPlaySoundHighScore = true;
                }

                break;
            case GameUtil.PAUSE_STATE:

                //Check if the current screen is pause screen
                if (currentScreen != GameUtil.PAUSE_STATE) {
                    //Change the screen to pause screen
                    Main.nifty.gotoScreen("pauseScreen");
                    currentScreen = GameUtil.PAUSE_STATE;
                }

                break;
            case GameUtil.RES_STATE:
                //Check if the current screen is result screen
                if (currentScreen != GameUtil.RES_STATE) {
                    //Play the sound effect
                    die.playInstance();

                    //Change the screen to result screen
                    Main.nifty.gotoScreen("resultScreen");
                    currentScreen = GameUtil.RES_STATE;
                    if (score > highScore) {
                        highScore = score;

                        //Try to save the highscore
                        try {
                            GameUtil.getInstance().saveHighScore(highScore);
                        } catch (IOException ex) {
                            System.out.println("FAILED TO SAVE HEHEHEHE");
                        }
                    }
                    GameUtil.getInstance().setLevel(1);
                }

                //Set the label text (high score and score)
                Main.nifty.getScreen("resultScreen").findElementById("labelHighScore").getNiftyControl(Label.class).setText(highScore + "");
                Main.nifty.getScreen("resultScreen").findElementById("labelScore").getNiftyControl(Label.class).setText(score + "");

                break;
            case GameUtil.MAIN_STATE:
                //Check if the current screen is main screen
                if (currentScreen != GameUtil.MAIN_STATE) {
                    //Change the screen to main screen
                    Main.nifty.gotoScreen("mainScreen");
                    currentScreen = GameUtil.MAIN_STATE;

                    //Reset the score and the object state
                    score = 0;
                    tempScore = 0;
                    try {
                        highScore = GameUtil.getInstance().loadHighScore();
                    } catch (IOException ex) {
                        Logger.getLogger(RunningGameState.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    isPlaySoundHighScore = false;
                    player.restartGame();
                    particle.setLocalTranslation(player.getX(), player.getY(), player.getZ());
                    obstacleFactory.restartGame();
                    terrainFactory.restartGame();
                }

                break;
            case GameUtil.QUIT_STATE:
                //Close the app when the state is in quit state
                app.stop();
        }
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {

    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {
    }

    /**
     * Setter for pause variable
     *
     * @param newVal true if the game is paused (stop)
     */
    public static void setPause(boolean newVal) {
        RunningGameState.pause = newVal;
    }

    /**
     * Getter for pause variable
     *
     * @return true if the game is paused / stopped
     */
    public static boolean isPause() {
        return pause;
    }

    /**
     * Method that was called from the xml to resume the game
     */
    public void continueGame() {
        GameUtil.setNextState(GameUtil.RUN_STATE);
        setPause(false);
    }

    /**
     * Method that was called from the xml to restart the game
     */
    public void restartGame() {
        GameUtil.setNextState(GameUtil.MAIN_STATE);
        setPause(true);
    }

    /**
     * Method that was called from the xml to quit the game
     */
    public void quitGame() {
        GameUtil.setNextState(GameUtil.QUIT_STATE);
    }
}
