package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.PickUpAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.type.PowerUpSearchResult;

/**
 * {@code PickUpActionChecker} checks {@code PickUpAction}
 * feasibility.
 */
public class PickUpActionChecker extends Checker {

    /**
     * Creates a new {@code PickUpActionChecker} from
     * both cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public PickUpActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    /**
     * Attaches an action to the checker.
     * @param action action to check
     * @return this {@code PickUpActionChecker} containing
     *         the action
     */
    public PickUpActionChecker is(Action action) {
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
        /**
         * Pick up actions are only feasible if the power up
         * detector detects the power up to pick.
         */
        boolean forced = ((PickUpAction) this.action).isForced();
        PowerUp powerUp = ((PickUpAction) this.action).toPowerUp();

        PowerUpSearchResult result = this.powerUpDetector.searchPowerUp(
                powerUp,
                forced
        );

        this.actionToExecute = result.action;
        return result.available;
    }
}