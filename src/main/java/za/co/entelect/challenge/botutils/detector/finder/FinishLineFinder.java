package za.co.entelect.challenge.botutils.detector.finder;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.type.DetectionParams;
import za.co.entelect.challenge.botutils.detector.Finder;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.entities.Block;

public class FinishLineFinder extends Finder {

    public FinishLineFinder(ObstacleDetector detector) {
        super(detector);
    }

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
