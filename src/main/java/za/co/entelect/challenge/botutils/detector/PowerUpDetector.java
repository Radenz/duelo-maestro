package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.Detector;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.type.DetectionParams;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.PowerUpSearchResult;

/**
 * {@code ObstacleDetector} is a detector to detect nearby
 * power ups in a car's surrounding.
 */
public class PowerUpDetector extends Detector {

    /**
     * Actions priority to search for power ups.
     */
    private static final GoAction[] GO_ACTIONS = {
            GoAction.ACCELERATE,
            GoAction.NORMAL,
            GoAction.TURN_LEFT,
            GoAction.TURN_RIGHT,
            GoAction.DECELERATE,
    };

    /**
     * Actions priority to search for nearest power ups.
     */
    private static final GoAction[] GO_ACTIONS_REVERSED = {
            GoAction.DECELERATE,
            GoAction.TURN_RIGHT,
            GoAction.TURN_LEFT,
            GoAction.NORMAL,
            GoAction.ACCELERATE,
    };

    /**
     * Creates a new {@code PowerUpDetector} for specified
     * {@code Car} and current {@code LocalMap}.
     * @param car a {@code Car} to attach to this detector
     * @param map current {@code LocalMap}
     */
    public PowerUpDetector(Car car, LocalMap map) {
        this.car = car;
        this.map = map;
    }

    /**
     * Searches specific power up using all {@code GoAction}.
     * @param powerUp power up to search
     * @param forced true if the searching process ignores
     *               hitting obstacles
     * @return a {@code PowerUpSearchResult} containing power up
     *         availability and {@code GoAction} to do to reach
     *         the power up
     */
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

    /**
     * Searches the nearest available power up using all
     * {@code GoAction}.
     * @return a {@code PowerUpSearchResult} containing power up
     *         availability and {@code GoAction} to do to reach
     *         the power up
     */
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
