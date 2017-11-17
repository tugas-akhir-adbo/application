/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import model.GameObject;
import model.GameUtil;
import model.Player;

/**
 * This class is class that control character (controller for character)
 * @author i16012 
 */
public class MyCharacterControl extends CharacterControl implements ChangeLaneControl {

    private Player objRep; //player's object
    private float vChangeLane, //speed of object when changing lane
				moveDistanceTemp; //distance the object has moved

    /**
     * Constructor
     * @param shape collision shape of object
     * @param stepHeight steps of a certain height / steepness of object
     */
    public MyCharacterControl(CollisionShape shape, float stepHeight) {
        super(shape, stepHeight);
    }

    /**
     * Method to control the character to jump
     */
    @Override
    public void jump() {
        if (!objRep.isJumping()) {
            objRep.setJumping(true);
            objRep.setVy(jumpSpeed);
        }
    }

    /**
     * Method to control the character to change lane
     * @param lane the lane the player wants
     */
    @Override
    public void changeLane(byte lane) {
        if (!objRep.isChangingLaneToLeft() && !objRep.isChangingLaneToRight()) {
            if (lane == GameUtil.LEFT_LANE) {
                if (objRep.getCurrentLane() == GameUtil.LEFT_LANE) {
                    return;
                }
                objRep.setChangingLaneToLeft(true);
            } else if (lane == GameUtil.RIGHT_LANE) {
                if (objRep.getCurrentLane() == GameUtil.RIGHT_LANE) {
                    return;
                }
                objRep.setChangingLaneToRight(true);
            }
            vChangeLane = GameUtil.V_CHANGE_LANE;
        }
    }

    /**
     * Method to update object 
     * @param tpf time per frame
     */
    @Override
    public void update(float tpf) {
        if (objRep.isJumping()) {
            objRep.rotateToFrontAnimation(tpf);
            if (this.objRep.getVy() != 0) {
                this.objRep.setVy(this.objRep.getVy() - getGravity() * tpf);
                this.objRep.setY(this.objRep.getY() + this.objRep.getVy() * tpf);
            }

            if (this.objRep.getSpatial().getLocalTranslation().y < this.objRep.getHeight() / 2) {
                this.objRep.setY((this.objRep.getHeight() / 2) + 7);
                this.objRep.setVy(0);

                ((Player) objRep).setJumping(false);
            }
        } else {
            if (RunningGameState.getIsRunning()) {
                objRep.normalRotationAnimation(tpf);
            }
        }

        if (objRep.isChangingLaneToLeft() || objRep.isChangingLaneToRight()) {
            vChangeLane -= tpf * GameUtil.ACCELERATOR_CHANGE_LANE;
            if (objRep.isChangingLaneToLeft()) {
                moveDistanceTemp += tpf * vChangeLane;
                objRep.setX(objRep.getX() - (tpf * vChangeLane));
                if (moveDistanceTemp > GameUtil.CHANGE_LANE_DISTANCE) {
                    if (objRep.getCurrentLane() == GameUtil.CENTER_LANE) {
                        objRep.setX(GameUtil.CHANGE_LANE_DISTANCE * -1);
                    } else if (objRep.getCurrentLane() == GameUtil.RIGHT_LANE) {
                        objRep.setX(0);
                    }
                    moveDistanceTemp = 0;
                    vChangeLane = 0;
                    objRep.setCurrentLane((byte) (objRep.getCurrentLane() - 1));
                    objRep.setChangingLaneToLeft(false);
                }
                objRep.rotateToLeftAnimation(tpf);
            } else if (objRep.isChangingLaneToRight()) {
                moveDistanceTemp += tpf * vChangeLane;
                objRep.setX(objRep.getX() + (tpf * vChangeLane));
                if (moveDistanceTemp > GameUtil.CHANGE_LANE_DISTANCE) {
                    if (objRep.getCurrentLane() == GameUtil.CENTER_LANE) {
                        objRep.setX(GameUtil.CHANGE_LANE_DISTANCE);
                    } else if (objRep.getCurrentLane() == GameUtil.LEFT_LANE) {
                        objRep.setX(0);
                    }
                    moveDistanceTemp = 0;
                    objRep.setCurrentLane((byte) (objRep.getCurrentLane() + 1));
                    vChangeLane = 0;
                    objRep.setChangingLaneToRight(false);
                }
                objRep.rotateToRightAnimation(tpf);
            }
        }
    }

    /**
     * Setter for player
     * @param obj player's object
     */
    public void setPlayer(Player obj) {
        this.objRep = obj;
    }
}
