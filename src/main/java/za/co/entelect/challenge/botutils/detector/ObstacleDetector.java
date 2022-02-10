package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.Detector;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.botutils.detector.finder.FinishLineFinder;
import za.co.entelect.challenge.botutils.detector.finder.ObstacleFinder;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.ObstacleType;

public class ObstacleDetector extends Detector {

    private boolean leftBlocked;
    private boolean rightBlocked;
    private boolean straightBlocked;
    private boolean accBlocked;
    private boolean leftHardBlocked;
    private boolean rightHardBlocked;
    private boolean straightHardBlocked;
    private boolean accHardBlocked;

    public ObstacleDetector(Car car, LocalMap map) {
        this.car = car;
        this.map = map;
        this.detectBlockade();
    }

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

    public Car getCar() {
        return this.car;
    }

    public LocalMap getMap() {
        return this.map;
    }

    public ObstacleFinder willHit(ObstacleType type) {
        return new ObstacleFinder(this, type);
    }

    public FinishLineFinder willReachFinish() {
        return new FinishLineFinder(this);

    }

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

    public boolean isStraightBlocked() {
        return this.straightBlocked;
    }

    public boolean isLeftBlocked() {
        return this.leftBlocked || !this.car.canTurnLeft();
    }

    public boolean isRightBlocked() {
        return this.rightBlocked || !this.car.canTurnRight();
    }

    public boolean isAccBlocked() {
        return this.accBlocked;
    }

    public boolean isLeftHardBlocked() {
        return this.leftHardBlocked || !this.car.canTurnLeft();
    }

    public boolean isRightHardBlocked() {
        return this.rightHardBlocked || !this.car.canTurnRight();
    }

    public boolean isStraightHardBlocked() {
        return this.straightHardBlocked;
    }

    public boolean isAccHardBlocked() {
        return this.accHardBlocked;
    }

    public boolean isNextToHardObstacle() {
        Block block = this.map.getBlock(this.car.lane(),
                this.car.at() + 1);
        return block.isHardObstacle();
    }

    public boolean canSeeHardObstaclesLeftSide() {
        if (this.car.isInLeftmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.car.lane() - 1,
                this.car.at());
        return block.isHardObstacle();
    }

    public boolean canSeeHardObstaclesRightSide() {
        if (this.car.isInRightmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.car.lane() + 1,
                this.car.at());
        return block.isHardObstacle();
    }

}
