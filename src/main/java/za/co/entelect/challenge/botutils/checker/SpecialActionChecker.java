package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.SpecialAction;
import za.co.entelect.challenge.botutils.action.UseAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.enums.State;
import za.co.entelect.challenge.type.PowerUpSearchResult;

public class SpecialActionChecker extends Checker {

    private static final UseAction[] BERSERK_ACTIONS = {
            UseAction.EMP_FORCE,
            UseAction.CYBERTRUCK_FORCE,
            UseAction.BOOST_FORCE,
            UseAction.OIL_FORCE,
            UseAction.LIZARD_FORCE
    };

    public SpecialActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    public SpecialActionChecker is(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public boolean feasible() {
        switch ((SpecialAction) this.action) {
            case BERSERK:
                this.actionToExecute = this.berserk();
                return this.actionToExecute != null
                        && this.self.hasAnyPowerUp() && (
                        this.self.nearlyFinish()
                                || this.opponent.nearlyFinish()
                );
            case ULTRA_BERSERK:
                this.actionToExecute = this.berserk();
                return  this.actionToExecute != null
                        && this.self.hasAnyPowerUp() && (
                        this.self.hasPastHalfway()
                                || this.opponent.hasPastHalfway()
                );
            case RAGE:

                if (this.self.has(PowerUp.EMP)
                        && this.positionDetector
                        .getOpponentRelativePosition()
                        .isWithinEMPRange()
                        && this.positionDetector
                        .isBehindOpponent()) {
                    this.actionToExecute = UseAction.EMP;
                } else {
                    this.actionToExecute = UseAction.CYBERTRUCK;
                }

                return this.self.has(PowerUp.TWEET) || (
                        this.self.has(PowerUp.EMP)
                                && this.positionDetector
                                .getOpponentRelativePosition()
                                .isWithinEMPRange()
                                && this.positionDetector
                                .isBehindOpponent()
                );
            case EMP_COMBO:
                this.actionToExecute = UseAction.EMP;
                return this.self.has(PowerUp.EMP, 5)
                        || (
                        this.self.is(State.USED_EMP)
                                && this.self.has(PowerUp.EMP)
                );
            case GREEDY_PICK_UP:
            default:
                PowerUpSearchResult result = this.powerUpDetector
                        .searchNearestPowerUp();
                this.actionToExecute = result.action;
                return result.available;
        }
    }

    private UseAction berserk() {
        UseActionChecker useActionChecker = new UseActionChecker(
                this.self,
                this.opponent
        );
        useActionChecker.use(this.obstacleDetector);
        useActionChecker.use(this.powerUpDetector);
        useActionChecker.use(this.positionDetector);
        for (UseAction action : SpecialActionChecker.BERSERK_ACTIONS) {
            if (useActionChecker.is(action).feasible()) {
                return action;
            }
        }
        if (this.self.has(PowerUp.BOOST)) {
            return UseAction.BOOST;
        }
        if (this.self.has(PowerUp.LIZARD)) {
            return UseAction.LIZARD;
        }
        if (this.self.has(PowerUp.TWEET)) {
            return UseAction.CYBERTRUCK;
        }
        if (this.positionDetector.isAheadOfOpponent()
                && this.self.has(PowerUp.OIL)) {
            return UseAction.OIL;
        }
        if (this.positionDetector.isBehindOpponent()
                && this.self.has(PowerUp.EMP)) {
            return UseAction.EMP;
        }
        return null;
    }
}