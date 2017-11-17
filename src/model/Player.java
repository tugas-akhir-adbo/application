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
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import controller.ChangeLaneControl;
import controller.MyCharacterControl;

/**
 * This class is class for object player (character)
 * @author i16012
 */
public class Player extends GameObject {

    private static final int ROTATE_FRONT = -3;

    private MyCharacterControl myCharCon; //character's controller
    private boolean jumping, //attribute for check whether the object is jumping 
					changingLaneToLeft, //attribute for check whether the object is changing lane to left 
					changingLaneToRight; //attribute for check whether the object is changing lane to right
    private byte currentLane; //current lane of this object

    /**
     * Constructor
     * @param x Coordinate x depends on the parent node
     * @param y Coordinate y depends on the parent node
     * @param z Coordinate z depends on the parent node
     * @param vx The speed of this object is in the x axis
     * @param vy The speed of this object is in the y axis
     * @param vz The speed of this object is in the z axis
     * @param width The width of this object
     * @param height The height of this object
     * @param length The length of this object
     * @param type Geometric shape of this object
     * @param closedOrFlipped condition for this object
     * @param name The name for this object
     * @param am Asset Manager for this object
     * @param mat The material for this object
     * @param texture The texture for this object
     */
    public Player(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(x, y, z, vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    /**
     * Constructor
     * @param vx The speed of this object is in the x axis
     * @param vy The speed of this object is in the y axis
     * @param vz The speed of this object is in the z axis
     * @param width The width of this object
     * @param height The height of this object
     * @param length The length of this object
     * @param type Geometric shape of this object
     * @param closedOrFlipped condition for this object
     * @param name The name for this object
     * @param am Asset Manager for this object
     * @param mat The material for this object
     * @param texture The texture for this object
     */
    public Player(float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    /**
     * Constructor
     * @param width The width of this object
     * @param height The height of this object
     * @param length The length of this object
     * @param type Geometric shape of this object
     * @param closedOrFlipped condition for this object
     * @param name The name for this object
     * @param am Asset Manager for this object
     * @param mat The material for this object
     * @param texture The texture for this object
     */
    public Player(float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(width, height, length, type, closedOrFlipped, name, am, mat, texture);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    /**
     * Constructor
     * @param x Coordinate x depends on the parent node
     * @param y Coordinate y depends on the parent node
     * @param z Coordinate z depends on the parent node
     * @param vx The speed of this object is in the x axis
     * @param vy The speed of this object is in the y axis
     * @param vz The speed of this object is in the z axis
     * @param width The width of this object
     * @param height The height of this object
     * @param length The length of this object
     * @param name The name for this object
     * @param path The path for this object's model
     * @param am Asset Manager for this object
     */
    public Player(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, String name, String path, AssetManager am) {
        super(x, y, z, vx, vy, vz, width, height, length, name, path, am);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    /**
     * Constructor
     * @param name The name for this object
     * @param path The path for this object's model
     * @param am Asset Manager for this object
     */
    public Player(String name, String path, AssetManager am) {
        super(name, path, am);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    /**
     * Constructor 
     * @param obj player's object
     */
    public Player(Spatial obj) {
        super(obj);
        setMyCharControl();
        myCharCon.setPlayer(this);
        currentLane = 1;
    }

    /**
     * Setter for character's controller
     */
    private void setMyCharControl() {
        BoxCollisionShape boxShape = new BoxCollisionShape(new Vector3f(this.length / 2f, this.height / 2f, this.width / 2f));
        myCharCon = new MyCharacterControl(boxShape, 0.5f);
        myCharCon.setJumpSpeed(100);
        myCharCon.setGravity(75);
        this.spatial.addControl(myCharCon);
    }

    /**
     * Method to update character's controller
     * @param tpf time per frame
     */
    @Override
    protected void controlUpdate(float tpf) {
        myCharCon.update(tpf);
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     * Method to change lane of the object
     * @param lane the lane the player wants
     */
    public void changeLane(byte lane) {
        myCharCon.changeLane(lane);
    }

    /**
     * Method to make object jump
     */
    public void jump() {
        myCharCon.jump();
    }

    /**
     * Animation to rotate the spatial to front when jumping
     * @param tpf time per frame
     */
    public void rotateToFrontAnimation(float tpf) {
        this.spatial.rotate(tpf * ROTATE_FRONT * 5, 0, 0);
    }

    /**
     * Animation to rotate the spatial to left when changing lane
     * @param tpf time per frame
     */
    public void rotateToLeftAnimation(float tpf) {
        this.spatial.rotate(0, tpf * GameUtil.ROTATE_SIDE * -1f, 0);
//        tempRadians += tpf * GameUtil.ROTATE_SIDE * -1f;
    }

    /**
     * Animation to rotate the spatial to right when changing lane
     * @param tpf time per frame
     */
    public void rotateToRightAnimation(float tpf) {
        this.spatial.rotate(0, tpf * GameUtil.ROTATE_SIDE, 0);
//        tempRadians += tpf * GameUtil.ROTATE_SIDE;
    }

    /**
     * Animation to rotate the spatial to the front
     * @param tpf time per frame
     */
    public void normalRotationAnimation(float tpf) {
        this.spatial.rotate(tpf * ROTATE_FRONT, 0, 0);
    }

    /**
     * Method to check whether the object is jumping (getter)
     * @return true if the object is jumping, false if the object isn't jumping
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * Setter for variable jumping
     * @param jumping new value of variable jumping
     */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    /**
     * Method to check whether the object is changing lane to the left (getter)
     * @return true if the object is changing lane to the left, false if the object isn't changing lane to the left
     */
    public boolean isChangingLaneToLeft() {
        return changingLaneToLeft;
    }

    /**
     * Setter for variable changingLaneToLeft
     * @param changingLaneToLeft new value of variable changingLaneToLeft
     */
    public void setChangingLaneToLeft(boolean changingLaneToLeft) {
        this.changingLaneToLeft = changingLaneToLeft;
    }

    /**
     * Method to check whether the object is changing lane to the right (getter)
     * @return true if the object is changing lane to the right, false if the object isn't changing lane to the right
     */
    public boolean isChangingLaneToRight() {
        return changingLaneToRight;
    }

    /**
     * Setter for variable changingLaneToRight
     * @param changingLaneToRight new value of variable changingLaneToRight
     */
    public void setChangingLaneToRight(boolean changingLaneToRight) {
        this.changingLaneToRight = changingLaneToRight;
    }

    /**
     * Method to get current lane (getter)
     * @return current lane         
     */
    public byte getCurrentLane() {
        return currentLane;
    }

    /**
     * Setter for variable currentLane
     * @param currentLane new value of variable currentLane
     */
    public void setCurrentLane(byte currentLane) {
        this.currentLane = currentLane;
    }
}
