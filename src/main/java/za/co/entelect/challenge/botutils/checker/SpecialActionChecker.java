package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.SpecialAction;
import za.co.entelect.challenge.botutils.action.UseAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.enums.State;
import za.co.entelect.challenge.type.PowerUpSearchResult;

/**
 * {@code SpecialActionChecker} checks {@code SpecialAction}
 * feasibility.
 */
public class SpecialActionChecker extends Checker {

    /**
     * Berserk actions priority.
     */
    private static final UseAction[] BERSERK_ACTIONS = {
            UseAction.EMP_FORCE,
            UseAction.CYBERTRUCK_FORCE,
            UseAction.BOOST_FORCE,
            UseAction.OIL_FORCE,
            UseAction.LIZARD_FORCE
    };

    /**
     * Creates a new {@code SpecialActionChecker} from
     * both cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public SpecialActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    /**
     * Attaches an action to the checker.
     * @param action action to check
     * @return this {@code SpecialActionChecker} containing
     *         the action
     */
    public SpecialActionChecker is(Action action) {
        this.action = action;
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
        switch ((SpecialAction) this.action) {
            /**
             * Berserk action is feasible if player has
             * any power ups and either player or opponent
             * has reached last fifth of the race.
             */
            case BERSERK:
                this.actionToExecute = this.berserk();
                return this.actionToExecute != null
                        && this.self.hasAnyPowerUp() && (
                        this.self.nearlyFinish()
                                || this.opponent.nearlyFinish()
                );
            /**
             * Ultra berserk action is feasible if player has
             * any power ups and either player or opponent
             * has reached last half of the race.
             */
            case ULTRA_BERSERK:
                this.actionToExecute = this.berserk();
                return  this.actionToExecute != null
                        && this.self.hasAnyPowerUp() && (
                        this.self.hasPastHalfway()
                                || this.opponent.hasPastHalfway()
                );

            /**
             * Rage action is only feasible if the player
             * has TWEET power up or EMP power up and
             * in the reasonable position to use the EMP.
             */
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
            /**
             * EMP combo is feasible if the player has 5 EMPs
             * or the player used EMP in the last round.
             */
            case EMP_COMBO:
                this.actionToExecute = UseAction.EMP;
                return this.self.has(PowerUp.EMP, 5)
                        || (
                        this.self.is(State.USED_EMP)
                                && this.self.has(PowerUp.EMP)
                );
            /**
             * Greedy pick up action is feasible if there is any
             * reachable nearest power up to pick.
             */
            case GREEDY_PICK_UP:
            default:
                PowerUpSearchResult result = this.powerUpDetector
                        .searchNearestPowerUp();
                this.actionToExecute = result.action;
                return result.available;
        }
    }

    /**
     * Calculates the best possible action to berserk.
     * @return the action to execute to berserk
     */
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