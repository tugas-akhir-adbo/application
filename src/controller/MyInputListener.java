package controller;

import com.jme3.audio.AudioNode;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.scene.Node;
import model.GameUtil;
import model.Player;

/** This class represents as input listener for the players.
 * 
 * @author fakhry dinan idris
 */
public class MyInputListener implements AnalogListener, ActionListener {

    /**
     * This attribute represents the players from Player class.
     */
    private Player player;
    
    /**
     * This attribute represents jump node from AudioNode class.
     */
    private AudioNode jumpNode;

    /** This is the class constructor
     * 
     * @param player
     * @param jumpNode 
     */
    public MyInputListener(Player player, AudioNode jumpNode) {
        this.player = player;
        this.jumpNode = jumpNode;
    }

    /** This method represents as the player's object in the game.
     * 
     * If name(as player) attribute equals to "jump", the object in the game
     * will jump.
     * If name(as player) attribute equals to "right", the object in the game
     * will change lane to right. 
     * If name(as player) attribute equals to "jump", the object in the game
     * will change lane to left.
     * 
     * @param name
     * @param value
     * @param tpf 
     */
    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!RunningGameState.isPause() && GameUtil.getCurrentState() != GameUtil.MAIN_STATE && GameUtil.getCurrentState() != GameUtil.RES_STATE) {
            if (name.equals("Jump")) {
                if (!player.isJumping()) jumpNode.playInstance();
                player.jump();
            }
            if (name.equals("Right")) {
                player.changeLane(GameUtil.RIGHT_LANE);
            }
            if (name.equals("Left")) {
                player.changeLane(GameUtil.LEFT_LANE);
            }
        }
    }

    /** This method represents as the player's object in the game.
     * 
     * If name(as player) attribute equals to "pause", the the game will
     * pause.
     * The next state, the game will running again.
     * 
     * @param name
     * @param isPressed
     * @param tpf 
     */
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Pause") && !isPressed && GameUtil.getCurrentState() != GameUtil.MAIN_STATE && GameUtil.getCurrentState() != GameUtil.RES_STATE) {
            if (RunningGameState.isPause()) {
                GameUtil.setNextState(GameUtil.RUN_STATE);
            } else {
                GameUtil.setNextState(GameUtil.PAUSE_STATE);
            }
            RunningGameState.setPause(!RunningGameState.isPause());
        }
    }

}
