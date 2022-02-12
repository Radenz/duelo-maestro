package za.co.entelect.challenge.botutils.detector.finder;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.type.DetectionParams;
import za.co.entelect.challenge.botutils.detector.Finder;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.entities.Block;

/**
 * {@code FinishLineFinder} is a {@code Finder} to
 * find finish line terrain.
 */
public class FinishLineFinder extends Finder {

    /**
     * Creates a new {@code FinishLineFinder} based on
     * {@code ObstacleDetector}.
     * @param detector {@code ObstacleDetector} to attach
     *                 to the finder
     */
    public FinishLineFinder(ObstacleDetector detector) {
        super(detector);
    }

    /**
     * Checks if the {@code ObstacleDetector}
     * detects finish line for specific {@code GoAction}.
     * @param action action to check
     * @return true if the finder's {@code ObstacleDetector}
     *         detects finish line for the action
     */
    public boolean when(GoAction action) {
        if (action == null) {
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
            if (block.isFinishLine()) {
                return true;
            }
        }

        return false;
    }
}
