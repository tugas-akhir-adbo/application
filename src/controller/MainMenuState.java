package controller;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import main.Main;
import model.GameUtil;

/**
 * This is Main State Class that appear when the app launched.
 * 
 * @author i16072 - Joshua Lauwrich N
 * @since Wednesday, 15 November 2017
 * @version 1.5
 */
public class MainMenuState extends BaseAppState implements ScreenController {

    private Main app;       //Application object
    private AppStateManager stateManager;   //State Manager
    private InputManager inputManager;      //Input Manager

    @Override
    protected void initialize(Application app) {
        //Initialize all the variable
        this.app = (Main) getApplication();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        
        //Load nifty GUI from xml file
        Main.nifty.fromXml("Interface/as.xml", "mainScreen", this);
        
        //Make the cursor visible and consider to be an input
        inputManager.setCursorVisible(true);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

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

    @Override
    public void update(float tpf) {
        if (Main.nifty != null) {
            super.update(tpf);
        }
    }

    /**
     * Method that was called from the xml file to start the game.
     * The state change to run state and the gui change to score board.
     */
    public void startGame() {
        //Change the nifty gui screen
        Main.nifty.gotoScreen("scoreBoard");
        
        //Change the next state to run state
        GameUtil.setNextState(GameUtil.RUN_STATE);
    }
}
