package model;

import controller.ChangeLaneControl;

/**
 *
 * @author i16087
 */
public class ChangingLaneObstacle extends ObstacleDecorator implements ChangeLaneControl {

    //a helper boolean field
    private boolean toLeft;

    /**
     * Constructor
     * @param wrappedObj object to be decorated
     */
    public ChangingLaneObstacle(Obstacle wrappedObj) {
        super(wrappedObj);
        this.vx = this.wrappedObj.getVx();
        this.vz = this.wrappedObj.getVz();
        this.z = this.wrappedObj.getZ();
        this.y = this.wrappedObj.getY();
    }

    @Override
    public void extraMove(float tpf) {
        if (toLeft) {
            if (x > GameUtil.CHANGE_LANE_DISTANCE * -1) {
                x -= vx * tpf;
            } else {
                toLeft = false;
            }
        } else {
            if (x < GameUtil.CHANGE_LANE_DISTANCE) {
                x += vx * tpf;
            } else {
                toLeft = true;
            }
        }
        this.spatial.rotate(5, 5, 5);
    }

    @Override
    public void changeLane(byte lane) {
        switch (lane) {
            //if current lane is left
            case GameUtil.LEFT_LANE:
                x = GameUtil.CHANGE_LANE_DISTANCE * -1;
                toLeft = false;
                break;
            //if current lane is centre
            case GameUtil.CENTER_LANE:
                x = 0;
                toLeft = true;
                break;
            //if current lane is right
            case GameUtil.RIGHT_LANE:
                x = GameUtil.CHANGE_LANE_DISTANCE;
                toLeft = true;
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        z += vz*tpf;
        extraMove(tpf);
        this.getSpatial().setLocalTranslation(x, y, z);
    }

}
