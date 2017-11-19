/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author Yehezkiel Rusli
 * 
 */
public class ConcreteObstacle extends Obstacle {
	
	/**
     * Constructor
	 * Attribute extends from GameObject
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
     * @param closedOrFlipped Condition for cylinder
     * @param name The name for this object
     * @param am Asset Manager for loading the model
     * @param mat The material of this object
     * @param texture The texture of this object
     */
    public ConcreteObstacle(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(x, y, z, vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
    }
/**
     * Constructor 
     * Attribute extends from GameObject
     * @param width The width of this object
     * @param height The height of this object
     * @param length The length of this object
     * @param type Geometric shape of this object
     * @param closedOrFlipped Condition for cylinder
     * @param name The name for this object
     * @param am Asset Manager for loading the model
     * @param mat The material of this object
     * @param texture The texture of this object
     */
    public ConcreteObstacle(float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(width, height, length, type, closedOrFlipped, name, am, mat, texture);
    }

	
	/**
     * Constructor
	 * Attribute extends from GameObject
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
     * @param am Asset Manager for loading the model
     */
    public ConcreteObstacle(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, String name, String path, AssetManager am) {
        super(x, y, z, vx, vy, vz, width, height, length, name, path, am);
    }

    /**
     * Constructor
	 * Attribute extends from GameObject
     * @param name The name for this object
	 * @param path The path for this object's model
     * @param am Asset Manager for loading the model
	 */
	public ConcreteObstacle(String name, String path, AssetManager am) {
        super(name, path, am);
    }
	
	/**
     * Constructor
	 * Attribute extends from GameObject
     * @param obj
	 */
	public ConcreteObstacle(Spatial obj) {
        super(obj);
    }
    
	/**
     * @param tpf time for moving
	 * set the value of z with the current possision
     */
	@Override
    protected void doAction(float tpf) {
        this.z += vz * tpf;
    }
    
}
