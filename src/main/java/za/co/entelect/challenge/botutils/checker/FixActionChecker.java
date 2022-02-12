package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.FixAction;
import za.co.entelect.challenge.entities.Car;

/**
 * {@code FixActionChecker} checks {@code FixAction}
 * feasibility.
 */
public class FixActionChecker extends Checker  {

    /**
     * Creates a new {@code FixActionChecker} from
     * both cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public FixActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    /**
     * Attaches an action to the checker.
     * @param action action to check
     * @return this {@code FixActionChecker} containing
     *         the action
     */
    public FixActionChecker is(Action action) {
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
        switch ((FixAction) this.action) {
            /**
             * Urgent fix action is feasible when
             * player's car damage is more than 5.
             */
            case URGENT:
                return this.self.damage >= 5;
            /**
             * Semi urgent fix action is feasible when
             * player's car damage is more than 3.
             */
            case SEMI_URGENT:
                return this.self.damage >= 3;
            /**
             * Normal fix action is always feasible.
             */
            case NORMAL:
            default:
                return true;
        }
    }
}
