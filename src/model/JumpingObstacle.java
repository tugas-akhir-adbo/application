package model;

/**
 *
 * @author i16087
 */
public class JumpingObstacle extends ObstacleDecorator {

    //object to be decorated
    private Obstacle wrappedObj;
    //a boolean helper (is going up or down)
    private boolean goingDown;
    //distance traveller
    private double tempDistance;
    
    /**
     * Constructor
     * @param wrappedObj object to be decorated
     */
    public JumpingObstacle(Obstacle wrappedObj) {
        super(wrappedObj);
        this.wrappedObj = wrappedObj;
        this.goingDown = true;
        this.vy = this.wrappedObj.getVy();
        this.vx = this.wrappedObj.getVx();
        this.vz = this.wrappedObj.getVz();
        this.x = this.wrappedObj.getX();
        this.z = this.wrappedObj.getZ();
        this.y = this.wrappedObj.getY();
    }

    @Override
    protected void extraMove(float tpf) {
        this.tempDistance += GameUtil.MOVING_SPEED_FOR_JUMPING_OBSTACLE * tpf;
        y = (float)Math.sin(tempDistance) * GameUtil.MAX_DISTANCE_FOR_JUMPING_OBSTACLE;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        z += vz*tpf;
//        System.out.println(z); 
        extraMove(tpf);
        this.getSpatial().setLocalTranslation(x, y, z);
    }
}
