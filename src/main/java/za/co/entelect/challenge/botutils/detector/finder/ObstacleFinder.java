package za.co.entelect.challenge.botutils.detector.finder;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.type.DetectionParams;
import za.co.entelect.challenge.botutils.detector.Finder;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.type.ObstacleType;

public class ObstacleFinder extends Finder {
    ObstacleType obstacleToFind;

    public ObstacleFinder(
            ObstacleDetector detector,
            ObstacleType obstacle
    ) {
        super(detector);
        this.obstacleToFind = obstacle;
    }

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
