package model;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;

/** A class that create objects from the Terrain class.
 *
 * @author fakhry dinan idris
 */
public class TerrainFactory {

    /**
     * Attribute to instantiate TerrainFactory class.
     */
    private static TerrainFactory instance;
    
    /**
     * The list attribute that sets up the list space for the terrain object.
     */
    private LinkedList<Terrain> terrainList;

    /** Method to instantiate TerrainFactory class.
     * 
     * As long as the value of the instance is null, this method will 
     * instantiate TerrainFactory class.
     * 
     * @return instance
     */
    public static TerrainFactory getInstance() {
        if (instance == null) {
            instance = new TerrainFactory();
        }
        return instance;
    }

    /** A Constructor to set up a list space for terrain objects.
     * 
     */
    private TerrainFactory() {
        terrainList = new LinkedList<>();
    }

    /** Method to add a terrain object to the list.
     * 
     * As long as terrainList is still isEmpty(), then terrain will be added
     * to the list.
     * 
     * @param name
     * @param am
     * @param parentNode 
     */
    public void addTerrain(String name, AssetManager am, Node parentNode) {
        Terrain terrain;
        if (terrainList.isEmpty()) {
            terrain = new Terrain(0, 0, -128, 0, 0, GameUtil.V_TERRAIN_MOVE, 512, 0, 512, name, "Scenes/MyTerrain.j3o", am);
        } else {
            terrain = new Terrain(0, 0, terrainList.getLast().getZ() - 512, 0, 0, GameUtil.V_TERRAIN_MOVE, 512, 0, 512, name, "Scenes/MyTerrain.j3o", am);
        }

        parentNode.attachChild(terrain.getSpatial());
        terrain.initRoad(am);

        terrainList.add(terrain);
    }

    /** Method to create in-game path.
     * 
     * This method sets the local translation of the road class and attaches 
     * the next node to the parent node.
     * 
     * @param terrain
     * @param am
     * @param parentNode 
     */
    public void buildRoad(Terrain terrain, AssetManager am, Node parentNode) {
        Spatial road = am.loadModel("Scenes/MyRoad.j3o");
        road.setLocalTranslation(0, 1, 0);
        parentNode.attachChild(road);
    }
    
    /** Updater method.
     * 
     * As long as temp.getZ() is larger than temp.getWidth(),
     * then the index of terrainList is reduced by 1.
     * Then temp.resetRoad().
     * 
     * @param tpf 
     */
    public void update(float tpf) {
        Iterator<Terrain> it = terrainList.iterator();
        while (it.hasNext()) {
            Terrain temp = it.next();
            temp.update(tpf);
            if (temp.getZ() > temp.getWidth()) {
                int idx = terrainList.indexOf(temp) - 1;
                if (idx < 0) {
                    temp.setZ(terrainList.get(terrainList.size() - 1).getZ() - 512);
                } else {
                    temp.setZ(terrainList.get(idx).getZ() - 512);
                }
                temp.resetRoad();
            }
        }
    }
}
