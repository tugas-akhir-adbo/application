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

    public static final byte BOX = 1;
    public static final byte CYLINDER = 2;
    public static final byte QUAD = 3;
    public static final byte SPHERE = 4;

    protected float x;
    protected float y;
    protected float z;
    protected float vx;
    protected float vy;
    protected float vz;
    protected float width;
    protected float height;
    protected float length;
    protected String name;

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
            case BOX:
                Box box = new Box(length / 2.0f, height / 2.0f, width / 2.0f);
                this.spatial = new Geometry(name, box);
                this.spatial.setLocalTranslation(x, y, z);
                break;
            case CYLINDER:
                Cylinder cylinder = new Cylinder(100, 100, length, width, closedOrFlipped);
                this.spatial = new Geometry(name, cylinder);
                this.spatial.setLocalTranslation(x, y, z);
                break;
            case QUAD:
                Quad quad = new Quad(length, height);
                this.spatial = new Geometry(name, quad);
                this.spatial.setLocalTranslation(x - length / 2, y - height / 2, z);
                break;
            case SPHERE:
                Sphere sphere = new Sphere(50, 50, length);
                this.spatial = new Geometry(name, sphere);
                this.spatial.setLocalTranslation(x, y, z);
                break;
        }
        mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
    }
    
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
            case BOX:
                Box box = new Box(length / 2.0f, height / 2.0f, width / 2.0f);
                this.spatial = new Geometry(name, box);
                break;
            case CYLINDER:
                Cylinder cylinder = new Cylinder(100, 100, length, width, closedOrFlipped);
                this.spatial = new Geometry(name, cylinder);
                break;
            case QUAD:
                Quad quad = new Quad(length, height);
                this.spatial = new Geometry(name, quad);
                break;
            case SPHERE:
                Sphere sphere = new Sphere(50, 50, length);
                this.spatial = new Geometry(name, sphere);
                break;
        }
        mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
    }
    
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
            case BOX:
                Box box = new Box(length / 2.0f, height / 2.0f, width / 2.0f);
                this.spatial = new Geometry(name, box);
                break;
            case CYLINDER:
                Cylinder cylinder = new Cylinder(100, 100, length, width, closedOrFlipped);
                this.spatial = new Geometry(name, cylinder);
                break;
            case QUAD:
                Quad quad = new Quad(length, height);
                this.spatial = new Geometry(name, quad);
                break;
            case SPHERE:
                Sphere sphere = new Sphere(50, 50, length);
                this.spatial = new Geometry(name, sphere);
                break;
        }
        mat.setTexture(name, texture);
        this.spatial.setMaterial(mat);
    }
    
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
    }
    
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

    @Override
    protected void controlUpdate(float tpf) {
        x += vx * tpf;
        y += vy * tpf;
        z += vz * tpf;
        this.spatial.setLocalTranslation(x, y, z);
    }

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

    /*
     * TODO : To be implemented in subclass.
     */
    @Override
    protected abstract void controlRender(RenderManager rm, ViewPort vp);

    @Override
    public abstract void onAnimChange(AnimControl control, AnimChannel channel, String animName);

    @Override
    public abstract void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName);
    
    
}
