package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.DetectionParams;

public abstract class Finder {
    protected ObstacleDetector detector;

    public Finder(ObstacleDetector detector) {
        this.detector = detector;
    }

    public DetectionParams calcDetectionParams(GoAction action) {
        return DetectionParams.from(this.detector.getCar(), action);
    }

    abstract public boolean when(GoAction action);
}
