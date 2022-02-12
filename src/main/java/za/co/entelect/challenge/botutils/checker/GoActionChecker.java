package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.ObstacleType;

/**
 * {@code GoActionChecker} checks {@code GoAction}
 * feasibility.
 */
public class GoActionChecker extends Checker {

    /**
     * Creates a new {@code GoActionChecker} from
     * both cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public GoActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    /**
     * Attaches an action to the checker.
     * @param action action to check
     * @return this {@code GoActionChecker} containing
     *         the action
     */
    public GoActionChecker is(Action action) {
        this.action = action;
        this.actionToExecute = action;
        return this;
    }

    /**
     * Checks whether the action to check is feasible or not.
     * Must be called only after calling {@code is()} method.
     * @return true if the action to check is feasible, false
     *         otherwise
     */
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
            /**
             * Accelerate go action is only feasible if
             * 1) Player's car has 0 speed and can gain more speed
             * 2) Player is hard blocked and can gain more speed
             * Otherwise,
             *  If player is hard blocked with acceleration but is not
             *  hard blocked, meaning hard obstacle on the current lane
             *  is only reachable when accelerating, then accelerate action
             *  is not feasible.
             *
             *  If player car is soft blocked and can gain more speed,
             *  then accelerate action is feasible if and only if the
             *  car will not hit hard obstacle if accelerated.
             *
             *  If accelerate action is still not feasible up until this
             *  point,
             *      If player car is soft blocked with acceleration, but
             *      is not soft-blocked, meaning soft obstacle on the current
             *      lane is only reachable when accelerating, then accelerate
             *      action is not feasible.
             *
             *      If accelerate action is still not feasible, then
             *      it is feasible if player car will not hit any obstacle
             *      when accelerated.
             */
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
            /**
             * Normal action is only feasible if player car is hard blocked,
             * player car is soft blocked but no reachable hard obstacles in
             * the current lane, or the current lane is not blocked at all.
             */
            case NORMAL:
                if (hardBlocked) {
                    return true;
                }
                if (softBlocked) {
                    return !this.obstacleDetector.isStraightHardBlocked();
                }
                return !this.obstacleDetector.isStraightBlocked()
                        && !this.obstacleDetector.isNextToHardObstacle();
            /**
             * Turn left action is not feasible if player car
             * is in the leftmost lane.
             * If player car is soft-blocked, then it is feasible
             * if and only if player car will not hit hard obstacles
             * after turning left;
             * Otherwise it is feasible if and only if there is no
             * obstacles in the left lane and player is not
             * currently hit by EMP.
             */
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
            /**
             * Turn right action is not feasible if player car
             * is in the rightmost lane.
             * If player car is soft-blocked, then it is feasible
             * if and only if player car will not hit hard obstacles
             * after turning right;
             * Otherwise it is feasible if and only if there is no
             * obstacles in the right lane and player is not
             * currently hit by EMP.
             */
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
            /**
             * Decelerate action is only feasible if player
             * car will not hit any obstacle when decelerated.
             */
            case DECELERATE:
                return !this.obstacleDetector
                        .willHit(ObstacleType.ALL).when((GoAction) this.action);
        }

        return false;
    }

}
