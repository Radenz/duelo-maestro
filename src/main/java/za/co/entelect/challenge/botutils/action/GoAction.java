package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.enums.Direction;

/**
 * {@code GoAction} contains various basic actions
 * in the game.
 */
public enum GoAction implements Action {
    /**
     * Accelerate the car.
     */
    ACCELERATE,
    /**
     * Do nothing.
     */
    NORMAL,
    /**
     * Switch to left side lane.
     */
    TURN_LEFT,
    /**
     * Switch to right side lane.
     */
    TURN_RIGHT,
    /**
     * Decelerate the car.
     */
    DECELERATE;

    public Command execute() {
        switch (this) {
            case ACCELERATE:
                return new AccelerateCommand();
            case NORMAL:
                return new DoNothingCommand();
            case TURN_LEFT:
                return new TurnCommand(Direction.LEFT);
            case TURN_RIGHT:
                return new TurnCommand(Direction.RIGHT);
            case DECELERATE:
            default:
                return new DecelerateCommand();
        }
    }
}
