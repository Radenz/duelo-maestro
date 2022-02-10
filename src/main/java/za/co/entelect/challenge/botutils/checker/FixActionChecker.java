package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.FixAction;
import za.co.entelect.challenge.entities.Car;

public class FixActionChecker extends Checker  {

    public FixActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    public FixActionChecker is(Action action) {
        this.action = action;
        this.actionToExecute = action;
        return this;
    }

    @Override
    public boolean feasible() {
        switch ((FixAction) this.action) {
            case URGENT:
                return this.self.damage >= 5;
            case SEMI_URGENT:
                return this.self.damage >= 3;
            case NORMAL:
            default:
                return true;
        }
    }
}
