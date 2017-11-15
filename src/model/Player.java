/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;
import controller.ChangeLaneControl;
import controller.MyCharacterControl;

/**
 *
 * @author i16072
 */
public class Player extends GameObject implements ChangeLaneControl {

    private static final int ROTATE_FRONT = -3;

    private MyCharacterControl myCharCon;
    private boolean jumping, changingLaneToLeft, changingLaneToRight;
    private float tempRadians, vChangeLane, moveDistanceTemp;
    private byte currentLane;

    public Player(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(x, y, z, vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    public Player(float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    public Player(float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(width, height, length, type, closedOrFlipped, name, am, mat, texture);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    public Player(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, String name, String path, AssetManager am) {
        super(x, y, z, vx, vy, vz, width, height, length, name, path, am);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    public Player(String name, String path, AssetManager am) {
        super(name, path, am);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    public Player(String name) {
        super(name);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    private void setMyCharControl() {
        BoxCollisionShape boxShape = new BoxCollisionShape(new Vector3f(this.length / 2f, this.height / 2f, this.width / 2f));
        myCharCon = new MyCharacterControl(boxShape, 0.5f);
        myCharCon.setJumpSpeed(100);
        myCharCon.setGravity(75);
        this.spatial.addControl(myCharCon);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    @Override
    protected void controlUpdate(float tpf) {
        if (jumping) {
            myCharCon.update(tpf);
            rotateToFrontAnimation(tpf);
        } else {
            normalRotationAnimation(tpf);
        }

        if (changingLaneToLeft || changingLaneToRight) {
            vChangeLane -= tpf * GameUtil.ACCELERATOR_CHANGE_LANE;
            if (changingLaneToLeft) {
                moveDistanceTemp += tpf * vChangeLane;
                x -= tpf * vChangeLane;
                if (moveDistanceTemp > GameUtil.CHANGE_LANE_DISTANCE) {
                    if (currentLane == GameUtil.CENTER_LANE) {
                        x = GameUtil.CHANGE_LANE_DISTANCE * -1;
                    } else if (currentLane == GameUtil.RIGHT_LANE) {
                        x = 0;
                    }
                    moveDistanceTemp = 0;
                    vChangeLane = 0;
                    currentLane--;
                    changingLaneToLeft = false;
                    this.spatial.rotate(0, tempRadians * -1f, 0);
                    tempRadians = 0;
                }
                rotateToLeftAnimation(tpf);
            } else if (changingLaneToRight) {
                moveDistanceTemp += tpf * vChangeLane;
                x += tpf * vChangeLane;
                if (moveDistanceTemp > GameUtil.CHANGE_LANE_DISTANCE) {
                    if (currentLane == GameUtil.CENTER_LANE) {
                        x = GameUtil.CHANGE_LANE_DISTANCE;
                    } else if (currentLane == GameUtil.LEFT_LANE) {
                        x = 0;
                    }
                    moveDistanceTemp = 0;
                    currentLane++;
                    vChangeLane = 0;
                    changingLaneToRight = false;
                    this.spatial.rotate(0, tempRadians * -1f, 0);
                    tempRadians = 0;
                }
                rotateToRightAnimation(tpf);
            }
        }

        this.spatial.setLocalTranslation(x, y, z);
    }

    @Override
    public void reset() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void changeLane(byte lane) {
        if (!changingLaneToLeft && !changingLaneToRight) {
            if (lane == GameUtil.LEFT_LANE) {
                if (currentLane == GameUtil.LEFT_LANE) {
                    return;
                }
                changingLaneToLeft = true;
            } else if (lane == GameUtil.RIGHT_LANE) {
                if (currentLane == GameUtil.RIGHT_LANE) {
                    return;
                }
                changingLaneToRight = true;
            }

            vChangeLane = GameUtil.V_CHANGE_LANE;
        }
    }

    public void jump() {
        if (!jumping) {
            myCharCon.jump();
            jumping = true;
        }
    }

    public void slide() {
        //TODO : IMPLEMENT HERE LATER
    }

    /**
     * Animation to rotate the spatial to front when jumping
     */
    private void rotateToFrontAnimation(float tpf) {
        this.spatial.rotate(tpf * ROTATE_FRONT * 5, 0, 0);
    }

    /**
     * Animation to rotate the spatial to left when changing lane
     */
    private void rotateToLeftAnimation(float tpf) {
        this.spatial.rotate(0, tpf * GameUtil.ROTATE_SIDE * -1f, 0);
        tempRadians += tpf * GameUtil.ROTATE_SIDE * -1f;
    }

    /**
     * Animation to rotate the spatial to right when changing lane
     */
    private void rotateToRightAnimation(float tpf) {
        this.spatial.rotate(0, tpf * GameUtil.ROTATE_SIDE, 0);
        tempRadians += tpf * GameUtil.ROTATE_SIDE;
    }

    private void normalRotationAnimation(float tpf) {
        this.spatial.rotate(tpf * ROTATE_FRONT, 0, 0);
    }

    /**
     *
     * @return
     */
    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

}
