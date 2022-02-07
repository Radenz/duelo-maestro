package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.enums.Direction;

public enum GoAction implements Action {
    ACCELERATE,
    NORMAL,
    TURN_LEFT,
    TURN_RIGHT,
    DECELERATE;

    public boolean isFeasibleFor(Bot bot) {
        boolean softBlocked = bot.canSeeObstaclesOnAllLanes(false);
        boolean softBlockedWithAcc = bot.canSeeObstaclesOnAllLanes(true);
        boolean hardBlocked = bot.canSeeHardObstaclesOnAllLanes(false);
        boolean hardBlockedWithAcc = bot.canSeeHardObstaclesOnAllLanes(true);
        switch (this) {
            case ACCELERATE:
                if (bot.player.speed == 0 && bot.canGainMoreSpeed()) {
                    return true;
                }
                if (hardBlocked && bot.canGainMoreSpeed()) {
                    return true;
                }
                if (hardBlockedWithAcc && !hardBlocked) {
                    return false;
                }
                if (softBlocked && bot.canGainMoreSpeed()) {
                    return !bot.isAccHardBlocked();
                }
                if (softBlockedWithAcc && !softBlocked) {
                    return false;
                }
                return !bot.isAccBlocked() && !bot.isNextToHardObstacle()
                        && !bot.stillHasBoost();
            case NORMAL:
                if (hardBlocked) {
                    return true;
                }
                if (softBlocked) {
                    return !bot.isStraightHardBlocked();
                }
                return !bot.isStraightBlocked() && !bot.isNextToHardObstacle();
            case TURN_LEFT:
                if (!bot.canTurnLeft()) {
                    return false;
                }
                if (softBlocked) {
                    return !bot.isLeftHardBlocked();
                }
                return !bot.isLeftBlocked() && !bot.canSeeHardObstaclesLeftSide()
                        && !bot.isHitByEMP();
            case TURN_RIGHT:
                if (!bot.canTurnRight()) {
                    return false;
                }
                if (softBlocked) {
                    return !bot.isRightHardBlocked();
                }
                return !bot.isRightBlocked() && !bot.canSeeHardObstaclesRightSide()
                        && !bot.isHitByEMP();
            case DECELERATE:
                return !bot.willHitObstacleIf(this);
        }
        return false;
    }


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
