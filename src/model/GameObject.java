package model;

import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import java.io.IOException;

/**
 * This object represents the basic object used in this game. 
 * All objects in this game will be extending this class.
 *
 * @author Joshua Lauwrich
 * @since Saturday, 4 November 2017
 * @version 2.0
 */
public abstract class GameObject extends AbstractControl {

    /**
     * Coordinate x depends on the parent node
     */
    protected float x;

    /**
     * Coordinate y depends on the parent node
     */
    protected float y;

    /**
     * Coordinate z depends on the parent node
     */
    protected float z;

    /**
     * The speed of this object is in the x axis

     */
    protected float vx;

    /**
     * The speed of this object is in the y axis

     */
    protected float vy;

    /**
     * The speed of this object is in the z axis

     */
    protected float vz;

    /**
     * The width of this object
     */
    protected float width;

    /**
     * The height of this object
     */
    protected float height;

    /**
     * The length of this object
     */
    protected float length;

    /**
     * The name for this object
     */
    protected String name;


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
     * @param closedOrFlipped Condition for cylinder
     * @param name The name for this object
     * @param am Asset Manager for loading the model
     * @param mat The material of this object
     * @param texture The texture of this object
     */
    public GameObject(float x, float y, float z, float vx, float vy, float vz,
            float width, float height, float length, byte type, boolean closedOrFlipped,
            String name, AssetManager am, Material mat, Texture texture) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.width = width;
        this.height = height;
        this.length = length;
        this.name = name;

        switch (type) {
            case GameUtil.BOX:
                Box box = new Box(length / 2.0f, height / 2.0f, width / 2.0f);
                this.spatial = new Geometry(name, box);
                this.spatial.setLocalTranslation(x, y, z);
                break;
            case GameUtil.CYLINDER:
                Cylinder cylinder = new Cylinder(100, 100, length, width, closedOrFlipped);
                this.spatial = new Geometry(name, cylinder);
                this.spatial.setLocalTranslation(x, y, z);
                break;
            case GameUtil.QUAD:
                Quad quad = new Quad(length, height);
                this.spatial = new Geometry(name, quad);
                this.spatial.setLocalTranslation(x - length / 2, y - height / 2, z);
                break;
            case GameUtil.SPHERE:
                Sphere sphere = new Sphere(50, 50, length);
                this.spatial = new Geometry(name, sphere);
                this.spatial.setLocalTranslation(x, y, z);
                break;
        }
        if (texture != null) mat.setTexture("ColorMap", texture);
        this.spatial.setMaterial(mat);
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
     * @param closedOrFlipped Condition for cylinder
     * @param name The name for this object
     * @param am Asset Manager for loading the model
     * @param mat The material of this object
     * @param texture The texture of this object
     */
    public GameObject(float vx, float vy, float vz,
            float width, float height, float length, byte type, boolean closedOrFlipped,
            String name, AssetManager am, Material mat, Texture texture) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.width = width;
        this.height = height;
        this.length = length;
        this.name = name;

        switch (type) {
            case GameUtil.BOX:
                Box box = new Box(length / 2.0f, height / 2.0f, width / 2.0f);
                this.spatial = new Geometry(name, box);
                break;
            case GameUtil.CYLINDER:
                Cylinder cylinder = new Cylinder(100, 100, length, width, closedOrFlipped);
                this.spatial = new Geometry(name, cylinder);
                break;
            case GameUtil.QUAD:
                Quad quad = new Quad(length, height);
                this.spatial = new Geometry(name, quad);
                break;
            case GameUtil.SPHERE:
                Sphere sphere = new Sphere(50, 50, length);
                this.spatial = new Geometry(name, sphere);
                break;
        }
        
        if (texture != null) mat.setTexture("ColorMap", texture);
        this.spatial.setMaterial(mat);
        
    }

    /**
     * Constructor
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
    public GameObject(float width, float height, float length, byte type, boolean closedOrFlipped,
            String name, AssetManager am, Material mat, Texture texture) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
        this.width = width;
        this.height = height;
        this.length = length;
        this.name = name;

        switch (type) {
            case GameUtil.BOX:
                Box box = new Box(length / 2.0f, height / 2.0f, width / 2.0f);
                this.spatial = new Geometry(name, box);
                break;
            case GameUtil.CYLINDER:
                Cylinder cylinder = new Cylinder(100, 100, length, width, closedOrFlipped);
                this.spatial = new Geometry(name, cylinder);
                break;
            case GameUtil.QUAD:
                Quad quad = new Quad(length, height);
                this.spatial = new Geometry(name, quad);
                break;
            case GameUtil.SPHERE:
                Sphere sphere = new Sphere(50, 50, length);
                this.spatial = new Geometry(name, sphere);
                break;
        }
        if (texture != null) mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
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
     * @param am Asset Manager for loading the model
     */
    public GameObject(float x, float y, float z, float vx, float vy, float vz,
            float width, float height, float length,
            String name, String path, AssetManager am) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.width = width;
        this.height = height;
        this.length = length;
        this.name = name;

        this.spatial = am.loadModel(path);
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     * Constructor
     * @param name The name for this object
     * @param path The path for this object's model
     * @param am Asset Manager for loading the model
     */
    public GameObject(String name, String path, AssetManager am) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.vx = 0;
        this.vy = 0;
        this.width = 0;
        this.height = 0;
        this.length = 0;
        this.name = name;

        this.spatial = am.loadModel(path);
    }

    /**
     * Constructor
     * @param obj 
     */
    public GameObject(Spatial obj) {
        this.spatial = obj;
    }
    
    /**
     * Write down the information of this object on the capsule so that 
     * when it is destroyed the information is kept.
     * @param ex JMEExporter object for saving this object.
     * @throws IOException
     */
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);

        OutputCapsule oc = ex.getCapsule(this);
        oc.write(y, "y", 0);
        oc.write(x, "x", 0);
        oc.write(z, "z", 0);
        oc.write(vx, "vx", 0);
        oc.write(vy, "vy", 0);
        oc.write(vz, "vz", 0);
        oc.write(width, "width", 0);
        oc.write(length, "length", 0);
        oc.write(height, "height", 0);
        oc.write(name, "name", "");
    }

    /**
     * Read the information of this object that has been saved previously.
     * @param im JMEImporter object for reading the information for this object.
     * @throws IOException
     */
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);

        InputCapsule ic = im.getCapsule(this);
        enabled = ic.readBoolean("enabled", true);
        spatial = (Spatial) ic.readSavable("spatial", null);
        x = ic.readFloat("x", 0);
        y = ic.readFloat("y", 0);
        z = ic.readFloat("z", 0);
        vx = ic.readFloat("vx", 0);
        vy = ic.readFloat("vy", 0);
        vz = ic.readFloat("vz", 0);
        width = ic.readFloat("width", 0);
        height = ic.readFloat("height", 0);
        length = ic.readFloat("length", 0);
        name = ic.readString("name", "");
    }

    /**
     * Attach this object to a node.
     * @param node parent node
     */
    public void attachToNode(Node node) {
        node.attachChild(this.spatial);
    }

    /**
     * Detach this object from a node
     * @param node parent node
     */
    public void dettachFromNode(Node node) {
        node.detachChild(this.spatial);
    }

    /**
     * Getter of variable x
     * @return Coordinate x depends on the parent node
     */
    public float getX() {
        return x;
    }

    /**
     * Setter of variable x
     * @param x Coordinate x depends on the parent node
     */
    public void setX(float x) {
        this.x = x;
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     * Getter of variable y
     * @return Coordinate y depends on the parent node
     */
    public float getY() {
        return y;
    }

    /**
     * Setter of variable y
     * @param y Coordinate y depends on the parent node
     */
    public void setY(float y) {
        this.y = y;
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     * Getter of variable z
     * @return Coordinate z depends on the parent node
     */
    public float getZ() {
        return z;
    }

    /**
     * Setter of variable z
     * @param z Coordinate z depends on the parent node
     */
    public void setZ(float z) {
        this.z = z;
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     * Getter of variabel vx
     * @return The speed of this object is in the x axis
     */
    public float getVx() {
        return vx;
    }

    /**
     * Setter for variable vx
     * @param vx New speed for this object in x axis
     */
    public void setVx(float vx) {
        this.vx = vx;
    }

    /**
     * Getter of variabel vy
     * @return The speed of this object is in the y axis
     */
    public float getVy() {
        return vy;
    }

    /**
     * Setter for variable vy
     * @param vy New speed for this object in y axis
     */
    public void setVy(float vy) {
        this.vy = vy;
    }

    /**
     * Getter of variabel vz
     * @return The speed of this object is in the z axis
     */
    public float getVz() {
        return vz;
    }

    /**
     * Setter for variable vz
     * @param vz New speed for this object in z axis
     */
    public void setVz(float vz) {
        this.vz = vz;
    }

    /**
     * Getter for variable width
     * @return The width of this object
     */
    public float getWidth() {
        return width;
    }

    /**
     * Getter for variable height
     * @return The height of this object
     */
    public float getHeight() {
        return height;
    }

    /**
     * Getter for variable length
     * @return The lengt of this object
     */
    public float getLength() {
        return length;
    }

    /**
     * Getter for variable name
     * @return The name of this object
     */
    public String getName() {
        return name;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //NOTHING TO DO
    }

    /*
     * To be implemented in subclass.
     */

    @Override
    protected abstract void controlUpdate(float tpf);
}
