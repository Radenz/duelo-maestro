package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.botutils.action.UseAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.ObstacleType;

public class GoActionChecker extends Checker {

    public GoActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    public GoActionChecker is(Action action) {
        this.action = action;
        this.actionToExecute = action;
        return this;
    }

    @Override
    public boolean feasible() {
        boolean softBlocked = this.obstacleDetector.
                canSeeOnAllLanes(ObstacleType.ALL, false);
        boolean softBlockedWithAcc = this.obstacleDetector.
                canSeeOnAllLanes(ObstacleType.ALL, true);
        boolean hardBlocked = this.obstacleDetector.
                canSeeOnAllLanes(ObstacleType.HARD, false);
        boolean hardBlockedWithAcc = this.obstacleDetector.
                canSeeOnAllLanes(ObstacleType.HARD, true);

        switch ((GoAction) this.action) {
            case ACCELERATE:
                if (this.self.speed == 0 && this.self.canGainMoreSpeed()) {
                    return true;
                }
                if (hardBlocked && this.self.canGainMoreSpeed()) {
                    return true;
                }
                if (hardBlockedWithAcc && !hardBlocked) {
                    return false;
                }
                if (softBlocked && this.self.canGainMoreSpeed()) {
                    return !this.obstacleDetector.isAccHardBlocked();
                }
                if (softBlockedWithAcc && !softBlocked) {
                    return false;
                }
                return !this.obstacleDetector.isAccBlocked()
                        && !this.obstacleDetector.isNextToHardObstacle();
            case NORMAL:
                if (hardBlocked) {
                    return true;
                }
                if (softBlocked) {
                    return !this.obstacleDetector.isStraightHardBlocked();
                }
                return !this.obstacleDetector.isStraightBlocked()
                        && !this.obstacleDetector.isNextToHardObstacle();
            case TURN_LEFT:
                if (!this.self.canTurnLeft()) {
                    return false;
                }
                if (softBlocked) {
                    return !this.obstacleDetector.isLeftHardBlocked();
                }
                return !this.obstacleDetector.isLeftBlocked()
                        && !this.obstacleDetector.canSeeHardObstaclesLeftSide()
                        && !this.self.isHitByEMP();
            case TURN_RIGHT:
                if (!this.self.canTurnRight()) {
                    return false;
                }
                if (softBlocked) {
                    return !this.obstacleDetector.isRightHardBlocked();
                }
                return !this.obstacleDetector.isRightBlocked()
                        && !this.obstacleDetector.canSeeHardObstaclesRightSide()
                        && !this.self.isHitByEMP();
            case DECELERATE:
                return !this.obstacleDetector
                        .willHit(ObstacleType.ALL).when((GoAction) this.action);
        }

        return false;
    }

}
