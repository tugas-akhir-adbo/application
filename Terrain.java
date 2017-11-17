package model;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import java.util.Iterator;
import java.util.LinkedList;

/** Classes that represent terrain which are objects in the game
 *
 * @author fakhry dinan idris
 */
public class Terrain extends GameObject {

    /**
     * The list attribute that sets up the list space for the road object.
     */
    private LinkedList<Spatial> road;

    /** The constructor that initiates the values to be entered into the list of 
     * GameObject.
     * 
     * This constructor is an extended from GameObject.
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
    public Terrain(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(x, y, z, vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
        road = new LinkedList<>();
    }

    /** The constructor that initiates the values to be entered into the list of 
     * GameObject.
     * 
     * This constructor is an extended from GameObject.
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
    public Terrain(float vx, float vy, float vz, float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(vx, vy, vz, width, height, length, type, closedOrFlipped, name, am, mat, texture);
        road = new LinkedList<>();
    }

    /** The constructor that initiates the values to be entered into the list of 
     * GameObject.
     * 
     * This constructor is an extended from GameObject.
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
    public Terrain(float width, float height, float length, byte type, boolean closedOrFlipped, String name, AssetManager am, Material mat, Texture texture) {
        super(width, height, length, type, closedOrFlipped, name, am, mat, texture);
        road = new LinkedList<>();
    }

    /** The constructor that initiates the values to be entered into the list of 
     * GameObject.
     * 
     * This constructor is an extended from GameObject.
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
     * @param name
     * @param path
     * @param am 
     */
    public Terrain(float x, float y, float z, float vx, float vy, float vz, float width, float height, float length, String name, String path, AssetManager am) {
        super(x, y, z, vx, vy, vz, width, height, length, name, path, am);
        road = new LinkedList<>();
    }

    /** The constructor that initiates the values to be entered into the list of 
     * GameObject.
     * 
     * This constructor is an extended from GameObject.
     * 
     * @param name
     * @param path
     * @param am 
     */
    public Terrain(String name, String path, AssetManager am) {
        super(name, path, am);
        road = new LinkedList<>();
    }

    /** The constructor that initiates the values to be entered into the list of 
     * GameObject.
     * 
     * This constructor is an extended from GameObject.
     * 
     * @param obj 
     */
    public Terrain(Spatial obj) {
        super(obj);
        road = new LinkedList<>();
    }

    /** The methods that initiates the values of road attribute.
     * 
     * As long as 'i' is less than 5, this method will continue to add list
     * from road to roadTemp.
     * 
     * @param am 
     */
    public void initRoad(AssetManager am) {
        for (int i = 0; i < 5; i++) {
            Spatial roadTemp = am.loadModel("Scenes/MyRoad.j3o");
            roadTemp.setLocalTranslation(0, 3, z + (512f / 2f) - (128f / 2f) - (i * 128f));
            this.spatial.getParent().attachChild(roadTemp);
            this.road.add(roadTemp);
        }
    }

    /** Method that iterates the road attribute.
     * 
     * As long as the iterator class still has the next node, this method will 
     * continue to iterate iterator class
     */
    public void resetRoad() {
        Iterator<Spatial> it = road.iterator();
        int counter = 0;
        while (it.hasNext()) {
            it.next().setLocalTranslation(0, 3, z + (512f / 2f) - (128f / 2f) - (counter * 128f));
            counter++;
        }
    }

    /** Control update method.
     * 
     * This method overrides the method from
     * GameObject class.
     * As long as the spatial class still has the next node, this method will 
     * continue to set local translation.
     * 
     * @param tpf 
     */
    @Override
    protected void controlUpdate(float tpf) {
        this.z += vz * tpf;
        this.spatial.setLocalTranslation(x, y, z);

        Iterator<Spatial> it = road.iterator();

        while (it.hasNext()) {
            Spatial temp = it.next();
            temp.setLocalTranslation(x, temp.getLocalTranslation().y, temp.getLocalTranslation().z + (vz * tpf));
        }
    }

}
