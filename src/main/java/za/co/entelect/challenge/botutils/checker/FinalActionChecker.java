package za.co.entelect.challenge.botutils.checker;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.FinalAction;
import za.co.entelect.challenge.entities.Car;

public class FinalActionChecker extends Checker {

    public FinalActionChecker(Car self, Car opponent) {
        super(self, opponent);
    }

    public FinalActionChecker is(Action action) {
        this.action = action;
        this.actionToExecute = action;
        return this;
    }

    @Override
    public boolean feasible() {
        return this.obstacleDetector.willReachFinish()
                .when(((FinalAction) this.action).toGoAction());
    }
}