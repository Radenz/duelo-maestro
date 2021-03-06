package za.co.entelect.challenge.type;

import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Car;

/**
 * {@code DetectionParams} contains all parameters needed
 * to iterate blocks in {@code LocalMap} for a specific
 * {@code GoAction}.
 */
public class DetectionParams {
    public int diff;
    public int start;
    public int lane;

    private DetectionParams(int start, int lane, int diff) {
        this.start = start;
        this.lane = lane;
        this.diff = diff;
    }

    /**
     * Get {@code DetectionParams} of specific {@code Car} object
     * for specific {@code GoAction}.
     * @param car player car
     * @param action {@code GoAction} to do
     * @return detection parameters for current player car and
     * action
     */
    public static DetectionParams from(Car car, GoAction action) {
        int diff = car.speed;
        int lane = car.lane();
        int start = car.at();
        switch (action) {
            case ACCELERATE:
                diff = car.getAcceleratedSpeed();
                break;
            case DECELERATE:
                diff = car.getDeceleratedSpeed();
                break;
            case TURN_LEFT:
                lane -= 1;
                diff -= 1;
                break;
            case TURN_RIGHT:
                lane += 1;
                diff -= 1;
                break;
            default:
                break;
        }

        return new DetectionParams(start, lane, diff);
    }
}
