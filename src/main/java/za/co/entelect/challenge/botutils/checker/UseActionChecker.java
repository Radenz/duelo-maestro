package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.botutils.action.UseAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.ObstacleType;

public class UseActionChecker extends Checker {

    public UseActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    public UseActionChecker is(Action action) {
        this.action = action;
        this.actionToExecute = action;
        return this;
    }

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
            case OIL:
            case OIL_FORCE:
                feasibility = feasibility
                        && this.positionDetector.isAheadOfOpponent()
                        && this.positionDetector.isWithinEMPTrappingRange();
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