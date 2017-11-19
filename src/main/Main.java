package main;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import controller.MainMenuState;
import controller.RunningGameState;
import de.lessvoid.nifty.Nifty;
import model.GameUtil;

/**
 * This is the Main Class of of this game. 
 * This class initiate all state and handle all state transition.
 *
 * @author i16072 - Joshua Lauwrich N
 * @since Saturday, 4 November 2017
 * @version 2.3
 */
public class Main extends SimpleApplication {

    private RunningGameState runningGameState;  //State when the game is running
    private MainMenuState mainMenuState;        //State that appear for the first time the app launched
    private AudioNode backSound;
    /**
     * Nifty GUI object that can be access from other class (state)
     */
    public static Nifty nifty;

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings s = new AppSettings(true);
        s.setFrameRate(60);
        s.setResolution(1280, 768);
        s.setFullscreen(true);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        app.setSettings(s);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //Initiate running game state and set it to disable
        runningGameState = new RunningGameState();
        runningGameState.setEnabled(false);
        
        //Initiate main menu state
        mainMenuState = new MainMenuState();
        
        //Set the default camera off
        flyCam.setEnabled(false);
        
        //Set all the state
        initState();
        
        //Start the background music
        initAudio();
        
        //Set current state to main state
        GameUtil.setCurrentState(0);
        
        //Initiate nifty object
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager,
                inputManager,
                getAudioRenderer(),
                getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        //Attach the nifty display to the gui view port as a processor
        getGuiViewPort().addProcessor(niftyDisplay);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //This block execute if there is a change on the state
        if (GameUtil.getCurrentState() != GameUtil.getNextState()) {
            switch (GameUtil.getNextState()) {
                case GameUtil.MAIN_STATE:
                    RunningGameState.setPause(true);
                    break;
                case GameUtil.RES_STATE:
                    break;
                case GameUtil.RUN_STATE:
                    RunningGameState.setPause(false);
                    stateManager.getState(RunningGameState.class).setEnabled(true);
                    stateManager.getState(MainMenuState.class).setEnabled(false);
                    break;
                case GameUtil.PAUSE_STATE:
                    break;
            }

            //Finally set current state from next state
            GameUtil.setCurrentState(GameUtil.getNextState());
        }

        listener.setLocation(new Vector3f(0, 0, 0));
        listener.setRotation(new Quaternion(0, 0, 0, 0));
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }

    /**
     * Attach all the state to the manager
     */
    private void initState() {
        stateManager.attach(runningGameState);
        stateManager.attach(mainMenuState);
    }

    /**
     * Set the back sound music
     */
    private void initAudio() {
        backSound = new AudioNode(assetManager, "Sounds/bm.ogg", DataType.Stream);
        backSound.setLooping(true);
        backSound.setPositional(false);
        backSound.setVolume(2);
        rootNode.attachChild(backSound);
        backSound.play();
    }
}
