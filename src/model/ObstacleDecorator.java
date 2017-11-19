package model;

/**
 * A decorator class
 * Define various obstacles with different capabilities
 * @author i16087
 */
public abstract class ObstacleDecorator extends Obstacle {
    
    /**
     * An instance of decorator object
     * to be decorated
     */
    protected Obstacle wrappedObj;
    
    /**
     * Constructor
     * @param wrappedObj an object to be decorated
     */
    public ObstacleDecorator(Obstacle wrappedObj) {
        super(wrappedObj.getSpatial());
        this.wrappedObj = wrappedObj;
    }
    
    /**
     * Extra capabilities to be decorated to
     * @param tpf 
     */
    protected abstract void extraMove(float tpf);

    /**
     * Do the extra capabilities
     * @param tpf 
     */
    @Override
    protected void doAction(float tpf) {
        this.wrappedObj.doAction(tpf);
    }
}
