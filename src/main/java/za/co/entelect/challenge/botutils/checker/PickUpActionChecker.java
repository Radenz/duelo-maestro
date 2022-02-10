package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.PickUpAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.type.PowerUpSearchResult;

public class PickUpActionChecker extends Checker {

    public PickUpActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    public PickUpActionChecker is(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public boolean feasible() {
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