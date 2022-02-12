package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.type.DetectionParams;

/**
 * {@code Finder} is a class that iterates blocks in
 * {@code LocalMap} to find specific terrain or
 * obstacle within the map.
 */
public abstract class Finder {
    protected ObstacleDetector detector;

    /**
     * Creates a new finder based on an {@code ObstacleDetector}.
     * @param detector an {@code ObstacleDetector}
     */
    public Finder(ObstacleDetector detector) {
        this.detector = detector;
    }

    /**
     * Calculates detection parameters based on specified action.
     * @param action action to calculate
     * @return detection parameter for the action
     */
    public DetectionParams calcDetectionParams(GoAction action) {
        return DetectionParams.from(this.detector.getCar(), action);
    }

    /**
     * Iterates travelled blocks in the {@code LocalMap} when
     * execution a specific {@code GoAction} to find specific
     * terrain.
     * @param action action to check
     * @return true if the terrain is found, false otherwise
     */
    abstract public boolean when(GoAction action);
}
