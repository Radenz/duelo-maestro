package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.Detector;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.type.DetectionParams;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.PowerUpSearchResult;

public class PowerUpDetector extends Detector {

    private static final GoAction[] GO_ACTIONS = {
            GoAction.ACCELERATE,
            GoAction.NORMAL,
            GoAction.TURN_LEFT,
            GoAction.TURN_RIGHT,
            GoAction.DECELERATE,
    };
    private static final GoAction[] GO_ACTIONS_REVERSED = {
            GoAction.DECELERATE,
            GoAction.TURN_RIGHT,
            GoAction.TURN_LEFT,
            GoAction.NORMAL,
            GoAction.ACCELERATE,
    };

    public PowerUpDetector(Car car, LocalMap map) {
        this.car = car;
        this.map = map;
    }

    public PowerUpSearchResult searchPowerUp(
            PowerUp powerUp,
            boolean forced
    ) {
        boolean found = false;
        GoAction actionToDo = GoAction.NORMAL;
        actions_iteration:
        for (GoAction action : PowerUpDetector.GO_ACTIONS) {
//            Guard
            if ((action == GoAction.TURN_LEFT && !this.car.canTurnLeft())
                    || (action == GoAction.TURN_RIGHT && !this.car.canTurnRight())) {
                continue;
            }

            DetectionParams params = DetectionParams.from(this.car, actionToDo);

            for (int i = 0; i < params.diff; i++) {
                int dist = params.start + i;
                Block block = this.map.getBlock(params.lane, dist);
//                Guard
                if (block == null) {
                    break;
                }
                if (block.terrain.is(powerUp)) {
                    found = true;
                    actionToDo = action;
                }
                if (block.isObstacle() && !forced) {
                    found = false;
                    continue actions_iteration;
                }

                if (block.isHardObstacle() && forced) {
                    found = false;
                    continue actions_iteration;
                }
            }
        }

        return new PowerUpSearchResult(found, actionToDo);
    }

    public PowerUpSearchResult searchNearestPowerUp() {
        boolean found = false;
        GoAction actionToDo = GoAction.NORMAL;
        actions_iteration:
        for (GoAction action : PowerUpDetector.GO_ACTIONS_REVERSED) {
//            Guard
            if ((action == GoAction.TURN_LEFT && !this.car.canTurnLeft())
                    || (action == GoAction.TURN_RIGHT && !this.car.canTurnRight())) {
                continue;
            }

            Car car = this.car.clone();

            DetectionParams params = DetectionParams.from(this.car, actionToDo);

            for (int i = 0; i < params.diff; i++) {
                int dist = params.start + i;
                Block block = this.map.getBlock(params.lane, dist);
//                Guard
                if (block == null) {
                    break;
                }
                if (block.terrain.isPowerUp()) {
                    found = true;
                    actionToDo = action;
                    break actions_iteration;
                }
                if (block.isSoftObstacle()) {
                    int speed = car.speed;
                    int newSpeed = car.decelerate();
                    params.diff -= speed - newSpeed;
                }

                if (block.isHardObstacle()) {
                    found = false;
                    continue actions_iteration;
                }
            }
        }

        return new PowerUpSearchResult(found, actionToDo);
    }
}
