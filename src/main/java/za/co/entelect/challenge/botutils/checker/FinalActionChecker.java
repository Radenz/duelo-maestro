package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.FinalAction;
import za.co.entelect.challenge.entities.Car;

/**
 * {@code FinalActionChecker} checks {@code FinalAction}
 * feasibility.
 */
public class FinalActionChecker extends Checker {

    /**
     * Creates a new {@code FinalActionChecker} from
     * both cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public FinalActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    /**
     * Attaches an action to the checker.
     * @param action action to check
     * @return this {@code FinalActionChecker} containing
     *         the action
     */
    public FinalActionChecker is(Action action) {
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
        /**
         * Final action is feasible only if player's car
         * will reach finish line if the action is executed.
         */
        return this.obstacleDetector.willReachFinish()
                .when(((FinalAction) this.action).toGoAction());
    }
}