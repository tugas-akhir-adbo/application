/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
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
 *
 * @author DarKnight98
 * @since Saturday, 4 November 2017
 * @version 1.0
 */
public abstract class GameObject extends AbstractControl implements AnimEventListener {

    /**
     *
     */
    protected float x;

    /**
     *
     */
    protected float y;

    /**
     *
     */
    protected float z;

    /**
     *
     */
    protected float vx;

    /**
     *
     */
    protected float vy;

    /**
     *
     */
    protected float vz;

    /**
     *
     */
    protected float width;

    /**
     *
     */
    protected float height;

    /**
     *
     */
    protected float length;

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected AnimChannel animChannel;

    /**
     *
     */
    protected AnimControl animControl;

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param vx
     * @param vy
     * @param vz
     * @param width
     * @param height
     * @param length
     * @param type
     * @param closedOrFlipped
     * @param name
     * @param am
     * @param mat
     * @param texture
     */
    public GameObject(float x, float y, float z, float vx, float vy, float vz,
            float width, float height, float length, byte type, boolean closedOrFlipped,
            String name, AssetManager am, Material mat, Texture texture) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
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
        mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
        
        setUpAnim();
    }

    /**
     *
     * @param vx
     * @param vy
     * @param vz
     * @param width
     * @param height
     * @param length
     * @param type
     * @param closedOrFlipped
     * @param name
     * @param am
     * @param mat
     * @param texture
     */
    public GameObject(float vx, float vy, float vz,
            float width, float height, float length, byte type, boolean closedOrFlipped,
            String name, AssetManager am, Material mat, Texture texture) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.vx = vx;
        this.vy = vy;
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
        mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
        
        setUpAnim();
    }

    /**
     *
     * @param width
     * @param height
     * @param length
     * @param type
     * @param closedOrFlipped
     * @param name
     * @param am
     * @param mat
     * @param texture
     */
    public GameObject(float width, float height, float length, byte type, boolean closedOrFlipped,
            String name, AssetManager am, Material mat, Texture texture) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.vx = 0;
        this.vy = 0;
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
        mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
        
        setUpAnim();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param vx
     * @param vy
     * @param vz
     * @param name
     * @param path
     * @param am
     */
    public GameObject(float x, float y, float z, float vx, float vy, float vz,
            String name, String path, AssetManager am) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.width = 0;
        this.height = 0;
        this.length = 0;
        this.name = name;

        this.spatial = am.loadModel(path);
        
        setUpAnim();
    }

    /**
     *
     * @param name
     * @param path
     * @param am
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
        
        setUpAnim();
    }

    /**
     *
     * @param name
     */
    public GameObject(String name) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.vx = 0;
        this.vy = 0;
        this.width = 0;
        this.height = 0;
        this.length = 0;
        this.name = name;
        
        setUpAnim();
    }
    
    /**
     *
     * @param ex
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
     *
     * @param im
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
     *
     * @param node
     */
    public void attachToNode(Node node) {
        node.attachChild(this.spatial);
    }

    /**
     *
     * @param node
     */
    public void dettachFromNode(Node node) {
        node.detachChild(this.spatial);
    }

    /**
     *
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(float x) {
        this.x = x;
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     *
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(float y) {
        this.y = y;
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     *
     * @return
     */
    public float getZ() {
        return z;
    }

    /**
     *
     * @param z
     */
    public void setZ(float z) {
        this.z = z;
        this.spatial.setLocalTranslation(x, y, z);
    }

    /**
     *
     * @return
     */
    public float getVx() {
        return vx;
    }

    /**
     *
     * @param vx
     */
    public void setVx(float vx) {
        this.vx = vx;
    }

    /**
     *
     * @return
     */
    public float getVy() {
        return vy;
    }

    /**
     *
     * @param vy
     */
    public void setVy(float vy) {
        this.vy = vy;
    }

    /**
     *
     * @return
     */
    public float getVz() {
        return vz;
    }

    /**
     *
     * @param vz
     */
    public void setVz(float vz) {
        this.vz = vz;
    }

    /**
     *
     * @return
     */
    public float getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public float getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public float getLength() {
        return length;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    private void setUpAnim() {
        animControl = this.spatial.getControl(AnimControl.class);
        animControl.addListener(this);
        animChannel = animControl.createChannel();
    }

    /*
     * To be implemented in subclass.
     */
    @Override
    protected abstract void controlRender(RenderManager rm, ViewPort vp);

    @Override
    public abstract void onAnimChange(AnimControl control, AnimChannel channel, String animName);

    @Override
    public abstract void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName);

    @Override
    protected abstract void controlUpdate(float tpf);
    
    /**
     *
     */
    public abstract void reset();
    
    /**
     *
     */
    public abstract void stop();
}
