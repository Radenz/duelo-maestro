package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.botutils.action.UseAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.ObstacleType;

/**
 * {@code UseActionChecker} checks {@code UseAction}
 * feasibility.
 */
public class UseActionChecker extends Checker {

    /**
     * Creates a new {@code UseActionChecker} from
     * both cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public UseActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    /**
     * Attaches an action to the checker.
     * @param action action to check
     * @return this {@code UseActionChecker} containing
     *         the action
     */
    public UseActionChecker is(Action action) {
        this.action = action;
        this.actionToExecute = action;
        return this;
    }

    /**
     * Checks whether the action to check is feasible or not.
     * Must be called only after calling {@code is()} method.
     * @return true if the action to check is feasible, false
     *         otherwise
     * @see UseAction
     */
    @Override
    public boolean feasible() {
        boolean willHitObstacle = this.obstacleDetector
                .willHit(ObstacleType.HARD).when(GoAction.NORMAL);
        if (this.action == UseAction.EMP_FORCE
                || this.action == UseAction.CYBERTRUCK_FORCE
                || this.action == UseAction.BOOST_FORCE
                || this.action == UseAction.OIL_FORCE
                || this.action == UseAction.LIZARD_FORCE) {
            willHitObstacle = false;
        }
        boolean feasibility = this.self.has(((UseAction) this.action).toPowerUp())
                && !willHitObstacle;
        switch ((UseAction) this.action) {
            case EMP:
            case EMP_FORCE:
                feasibility = feasibility
                        && this.positionDetector
                        .getOpponentRelativePosition().isWithinEMPRange()
                        && this.positionDetector.isBehindOpponent();
                break;
            case BOOST:
                feasibility = feasibility
                        && !this.obstacleDetector
                        .willHit(ObstacleType.HARD).whenBoosted()
                        && !this.self.isBoosting();
                break;
            case BOOST_FORCE:
                feasibility = feasibility
                        && !this.self.isBoosting();
                break;
            case OIL:
            case OIL_FORCE:
                feasibility = feasibility
                        && this.positionDetector.isAheadOfOpponent()
                        && this.positionDetector.isWithinTrappingRange();
                break;
            case LIZARD:
                feasibility = this.self.has(((UseAction) this.action).toPowerUp())
                        && this.obstacleDetector.isStraightBlocked()
                        && this.positionDetector.willBeSafeAfterLizardUses();
                break;
            case LIZARD_FORCE:
                feasibility = this.self.has(((UseAction) this.action).toPowerUp())
                        && this.obstacleDetector.isStraightBlocked();
                break;

        }
        return feasibility;
    }
}