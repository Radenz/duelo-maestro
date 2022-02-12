package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.Detector;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.botutils.detector.finder.FinishLineFinder;
import za.co.entelect.challenge.botutils.detector.finder.ObstacleFinder;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.ObstacleType;

/**
 * {@code ObstacleDetector} is a detector to detect nearby
 * obstacles in a car's surrounding.
 */
public class ObstacleDetector extends Detector {

    private boolean leftBlocked;
    private boolean rightBlocked;
    private boolean straightBlocked;
    private boolean accBlocked;
    private boolean leftHardBlocked;
    private boolean rightHardBlocked;
    private boolean straightHardBlocked;
    private boolean accHardBlocked;

    /**
     * Creates a new {@code ObstacleDetector} based on
     * {@code Car} object and current {@code LocalMap}.
     * @param car a {@code Car} to attach to this detector
     * @param map current {@code LocalMap}
     */
    public ObstacleDetector(Car car, LocalMap map) {
        this.car = car;
        this.map = map;
        this.detectBlockade();
    }

    /**
     * Detects blockade based on surrounding obstacle
     * relative to the car.
     */
    public void detectBlockade() {
        this.leftBlocked = !this.car.canTurnLeft()
                || this.willHit(ObstacleType.ALL).when(GoAction.TURN_LEFT);
        this.rightBlocked = !this.car.canTurnRight()
                || this.willHit(ObstacleType.ALL).when(GoAction.TURN_RIGHT);
        this.accBlocked = this.willHit(ObstacleType.ALL).when(GoAction.ACCELERATE);
        this.straightBlocked = this.willHit(ObstacleType.ALL).when(GoAction.NORMAL);
        this.leftHardBlocked = !this.car.canTurnLeft()
                || this.willHit(ObstacleType.HARD).when(GoAction.TURN_LEFT);
        this.rightHardBlocked = !this.car.canTurnRight()
                || this.willHit(ObstacleType.HARD).when(GoAction.TURN_RIGHT);
        this.accHardBlocked = this.willHit(ObstacleType.HARD).when(GoAction.ACCELERATE);
        this.straightHardBlocked = this.willHit(ObstacleType.HARD).when(GoAction.NORMAL);
    }

    /**
     * Gets the car attached to this {@code ObstacleDetector}.
     * @return the {@code Car} attached to this {@code ObstacleDetector}.
     */
    public Car getCar() {
        return this.car;
    }

    /**
     * Gets the local map attached to this {@code ObstacleDetector}.
     * @return the {@code LocalMap} attached to this {@code ObstacleDetector}.
     */
    public LocalMap getMap() {
        return this.map;
    }

    /**
     * Construct a new {@code ObstacleFinder} to find specific
     * obstacle type from this {@code ObstacleDetector}.
     * @param type obstacle type to find
     * @return {@code ObstacleFinder} to find specific the obstacle
     *         type.
     */
    public ObstacleFinder willHit(ObstacleType type) {
        return new ObstacleFinder(this, type);
    }

    /**
     * Construct a new {@code FinishLineFinder} from this
     * {@code ObstacleDetector}.
     * @return a {@code FinishLineFinder} that is attached with
     *         this {@code ObstacleDetector}
     */
    public FinishLineFinder willReachFinish() {
        return new FinishLineFinder(this);

    }

    /**
     * Detects specific obstacle type on all reachable lanes with
     * or without acceleration.
     * @param type obstacle type to detect
     * @param withAcceleration acceleration usage
     * @return true if this {@code ObstacleDetector} detects
     *         the specific obstacle on all reachable lanes
     */
    public boolean canSeeOnAllLanes(ObstacleType type, boolean withAcceleration) {
        switch (type) {
            case HARD:
                return this.leftHardBlocked && this.rightHardBlocked && (
                        withAcceleration ? this.accHardBlocked : this.straightHardBlocked
                );
            case ALL:
            case SOFT:
            default:
                return this.leftBlocked && this.rightBlocked && (
                        withAcceleration ? this.accBlocked : this.straightBlocked
                );
        }

    }

    /**
     * Retrieves the result of obstacle detection in the car's
     * current lane if the car is going straight without acceleration.
     * @return true if this {@code ObstacleDetector} detects
     *         an obstacle in the car's current lane.
     */
    public boolean isStraightBlocked() {
        return this.straightBlocked;
    }

    /**
     * Retrieves the result of obstacle detection in the car's
     * left side lane.
     * @return true if this {@code ObstacleDetector} detects
     *         an obstacle in the car's left side lane.
     */
    public boolean isLeftBlocked() {
        return this.leftBlocked || !this.car.canTurnLeft();
    }

    /**
     * Retrieves the result of obstacle detection in the car's
     * right side lane.
     * @return true if this {@code ObstacleDetector} detects
     *         an obstacle in the car's right side lane.
     */
    public boolean isRightBlocked() {
        return this.rightBlocked || !this.car.canTurnRight();
    }

    /**
     * Retrieves the result of obstacle detection in the car's
     * current lane
     * if the car is going straight with acceleration.
     * @return true if this {@code ObstacleDetector} detects
     *         an obstacle in the car's current lane.
     */
    public boolean isAccBlocked() {
        return this.accBlocked;
    }

    /**
     * Retrieves the result of hard obstacle detection in the car's
     * left side lane.
     * @return true if this {@code ObstacleDetector} detects
     *         a hard obstacle in the car's left side lane.
     */
    public boolean isLeftHardBlocked() {
        return this.leftHardBlocked || !this.car.canTurnLeft();
    }

    /**
     * Retrieves the result of hard obstacle detection in the car's
     * right side lane.
     * @return true if this {@code ObstacleDetector} detects
     *         a hard obstacle in the car's right side lane.
     */
    public boolean isRightHardBlocked() {
        return this.rightHardBlocked || !this.car.canTurnRight();
    }

    /**
     * Retrieves the result of hard obstacle detection in the car's
     * current lane if the car is going straight without acceleration.
     * if the car is going straight without acceleration.
     * @return true if this {@code ObstacleDetector} detects
     *         a hard obstacle in the car's current lane.
     */
    public boolean isStraightHardBlocked() {
        return this.straightHardBlocked;
    }

    /**
     * Retrieves the result of hard obstacle detection in the car's
     * current lane if the car is going straight with acceleration.
     * @return true if this {@code ObstacleDetector} detects
     *         a hard obstacle in the car's current lane.
     */
    public boolean isAccHardBlocked() {
        return this.accHardBlocked;
    }

    /**
     * Detects hard obstacles in front of the car.
     * @return true if the car is there is a hard obstacle
     *         directly in front of the car.
     */
    public boolean isNextToHardObstacle() {
        Block block = this.map.getBlock(this.car.lane(),
                this.car.at() + 1);
        return block.isHardObstacle();
    }

    /**
     * Detects hard obstacles in the car's left side lane.
     * @return true if this {@code ObstacleDetector} detects
     *         a hard obstacle in the car's left side lane.
     */
    public boolean canSeeHardObstaclesLeftSide() {
        if (this.car.isInLeftmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.car.lane() - 1,
                this.car.at());
        return block.isHardObstacle();
    }

    /**
     * Detects hard obstacles in the car's right side lane.
     * @return true if this {@code ObstacleDetector} detects
     *         a hard obstacle in the car's right side lane.
     */
    public boolean canSeeHardObstaclesRightSide() {
        if (this.car.isInRightmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.car.lane() + 1,
                this.car.at());
        return block.isHardObstacle();
    }

}
