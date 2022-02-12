package za.co.entelect.challenge.botutils.detector.finder;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.type.DetectionParams;
import za.co.entelect.challenge.botutils.detector.Finder;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.type.ObstacleType;

/**
 * {@code ObstacleFinder} is a {@code Finder} to
 * find obstacles.
 */
public class ObstacleFinder extends Finder {
    ObstacleType obstacleToFind;

    /**
     * Creates a new {@code ObstacleFinder} based on
     * {@code ObstacleDetector} and {@code ObstacleType}.
     * @param detector {@code ObstacleDetector} to attach
     *                 to the finder
     * @param obstacle {@code ObstacleType} to find
     */
    public ObstacleFinder(
            ObstacleDetector detector,
            ObstacleType obstacle
    ) {
        super(detector);
        this.obstacleToFind = obstacle;
    }

    /**
     * Checks if the {@code ObstacleDetector}
     * detects obstacle of type the {@code ObstacleType}
     * for specific {@code GoAction}.
     * @param action action to check
     * @return true if the finder's {@code ObstacleDetector}
     *         detects obstacle of type the {@code ObstacleType}
     *         for the action
     */
    public boolean when(GoAction action) {
        if (action == null || this.obstacleToFind == null) {
            return false;
        }

        DetectionParams params = this.calcDetectionParams(action);

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.detector.getMap().getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.is(this.obstacleToFind)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the {@code ObstacleDetector}
     * detects obstacle of type the {@code ObstacleType}
     * when the detector's car is boosted.
     * @return true if the finder's {@code ObstacleDetector}
     *         detects obstacle of type the {@code ObstacleType}
     *         when the detector's car is boosted
     */
    public boolean whenBoosted() {
        if (this.obstacleToFind == null) {
            return false;
        }

        DetectionParams params = this.calcDetectionParams(GoAction.NORMAL);
        params.diff = this.detector.getCar().maxSpeed();

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.detector.getMap().getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.is(this.obstacleToFind)) {
                return true;
            }
        }

        return false;

    }

}
