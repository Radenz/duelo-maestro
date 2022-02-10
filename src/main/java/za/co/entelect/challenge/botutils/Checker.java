package za.co.entelect.challenge.botutils;

import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.botutils.detector.PositionDetector;
import za.co.entelect.challenge.botutils.detector.PowerUpDetector;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.entities.Car;

public abstract class Checker {
    protected Action action;
    protected ObstacleDetector obstacleDetector;
    protected PowerUpDetector powerUpDetector;
    protected PositionDetector positionDetector;
    protected Car self;
    protected Car opponent;

    protected Action actionToExecute;

    public Checker(Car self, Car opponent) {
        this.self = self;
        this.opponent = opponent;
    }

    public void use(ObstacleDetector detector) {
        this.obstacleDetector = detector;
    }

    public void use(PowerUpDetector detector) {
        this.powerUpDetector = detector;
    }

    public void use(PositionDetector detector) {
        this.positionDetector = detector;
    }

    public abstract Checker is(Action action);
    public abstract boolean feasible();

    public Command execute() {
        return actionToExecute.execute();
    }

}
