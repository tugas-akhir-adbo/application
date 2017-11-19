package model;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Factory class (singleton) for generating the Terrain 
 * Terrain includes the floor and the 'road'
 * 
 * @author i16087
 */
public class TerrainFactory {

    //an instance of this class
    private static TerrainFactory instance;
    
    //the terrain used in game
    private LinkedList<Terrain> terrainList;

    
    /**
     * A method to return an instance of this class
     * 
     * @return an instance of this class
     */
    public static TerrainFactory getInstance() {
        if (instance == null) {
            instance = new TerrainFactory();
        }
        return instance;
    }
    
    /**
     * Constructor
     */
    private TerrainFactory() {
        terrainList = new LinkedList<>();
    }

    /**
     * Add a terrain 
     * @param name an id: a reference to a terrain object in the factory
     * @param am instance of the AssetManager
     * @param parentNode a node to attach to
     */
    public void addTerrain(String name, AssetManager am, Node parentNode) {
        Terrain terrain;
        if (terrainList.isEmpty()) {
            terrain = new Terrain(0, 0, -128, 0, 0, GameUtil.V_TERRAIN_MOVE, 512, 0, 512, name, "Scenes/MyTerrain.j3o", am);
        } else {
            terrain = new Terrain(0, 0, terrainList.getLast().getZ() - 512, 0, 0, GameUtil.V_TERRAIN_MOVE, 512, 0, 512, name, "Scenes/MyTerrain.j3o", am);
        }
        
//        buildRoad(terrain, am, parentNode);
        parentNode.attachChild(terrain.getSpatial());
        terrain.initRoad(am);
        
        terrainList.add(terrain);
    }
    
    /**
     * A method used to create/add road to terrain
     * @param terrain
     * @param am
     * @param parentNode 
     * @deprecated 
     */
    public void buildRoad(Terrain terrain, AssetManager am, Node parentNode) {
        Spatial road = am.loadModel("Scenes/MyRoad.j3o");
        road.setLocalTranslation(0, 1, 0);
        parentNode.attachChild(road);
    }
    
    /**
     * An update method
     * called on main loop update
     * 
     * @param tpf time per frame / refresh time
     */
    public void update (float tpf) {
        Iterator<Terrain> it = terrainList.iterator();
        while (it.hasNext()) {
            Terrain temp = it.next();
            temp.update(tpf);
            if (temp.getZ() > temp.getWidth()) {
                int idx = terrainList.indexOf(temp)-1;
                if (idx < 0) {
                    temp.setZ(terrainList.get(terrainList.size()-1).getZ() - 500);
                } else {
                    temp.setZ(terrainList.get(idx).getZ() - 512);
                }
                temp.resetRoad();
            }
        }
    }
    
    /**
     * A method to level up
     * leveling up accelerate the speed of the terrain
     */
    public void levelUp() {
        Iterator<Terrain> it = terrainList.iterator();
        while (it.hasNext()) {
            it.next().levelUp();
        }
    }
    
    /**
     * Method to restart the terrain
     */
    public void restartGame () {
        Iterator<Terrain> it = terrainList.iterator();
        while (it.hasNext()) {
            it.next().restartLevel();
        }
    }
}
