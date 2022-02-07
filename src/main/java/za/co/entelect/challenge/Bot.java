package za.co.entelect.challenge;

import za.co.entelect.challenge.botutils.Strategy;
import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.entities.*;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.enums.State;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.PowerUpSearchResult;
import za.co.entelect.challenge.type.RelativePosition;


public class Bot {

    private Car opponent;
    private Car player;

    private LocalMap map;
    private Strategy strategy = Strategy.SAFE;

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

    private boolean leftBlocked;
    private boolean rightBlocked;
    private boolean straightBlocked;
    private boolean accBlocked;
    private boolean leftHardBlocked;
    private boolean rightHardBlocked;
    private boolean straightHardBlocked;
    private boolean accHardBlocked;

    public Bot(GameState gameState) {
        this.player = gameState.player;
        this.opponent = gameState.opponent;
        this.map = LocalMap.from(gameState);
        this.calcBlockade();
    }

    public void useStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Command run() {

        Action[] actions = Strategy.getActions(this.strategy);
        for (Action action : actions) {
            if (action.isFeasibleFor(this)) {
                return action.execute();
            }
        }
        return new DoNothingCommand();
    }

    public Car player() {
        return this.player;
    }

    public Car opponent() {
        return this.opponent;
    }

    public boolean canTurnLeft() {
        return this.player.canTurnLeft();
    }

    public boolean canTurnRight() {
        return this.player.canTurnRight();
    }

    public boolean canGainMoreSpeed() {
        return this.player.canGainMoreSpeed();
    }

    public boolean willHitObstacleIf(GoAction action) {
        GoActionParams params = this.calcGoingParamsFor(
                Racer.SELF,
                action
        );

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.map.getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.isObstacle()) {
                return true;
            }
        }

        return false;
    }

    public boolean willOpponentHitObstacleIf(GoAction action) {
        GoActionParams params = this.calcGoingParamsFor(
                Racer.OPPONENT,
                action
        );

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.map.getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.isObstacle()) {
                return true;
            }
        }

        return false;
    }

    public boolean willHitHardObstacleIf(GoAction action) {
        GoActionParams params = this.calcGoingParamsFor(
                Racer.SELF,
                action
        );

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.map.getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.isHardObstacle()) {
                return true;
            }
        }

        return false;
    }

    public boolean willOpponentHitHardObstacleIf(GoAction action) {
        GoActionParams params = this.calcGoingParamsFor(
                Racer.OPPONENT,
                action
        );

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.map.getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.isHardObstacle()) {
                return true;
            }
        }

        return false;
    }

    public boolean willHitHardObstacleIfBoosted() {
        GoActionParams params = this.calcGoingParamsFor(
                Racer.SELF,
                GoAction.NORMAL
        );
        params.diff = this.player.maxSpeed();

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.map.getBlock(params.lane, dist);
//            Guard
            if (block == null) {
                continue;
            }
            if (block.isHardObstacle()) {
                return true;
            }
        }

        return false;
    }

    private GoActionParams calcGoingParamsFor(
            Racer racer,
            GoAction action
    ) {
        Car racerCar = racer == Racer.SELF
                ? this.player
                : this.opponent;
        int diff = racerCar.speed;
        int lane = racerCar.lane();
        int start = racerCar.at();
        switch (action) {
            case ACCELERATE:
                diff = racerCar.getAcceleratedSpeed();
                break;
            case DECELERATE:
                diff = racerCar.getDeceleratedSpeed();
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

        return new GoActionParams(start, lane, diff);
    }

    public boolean isBehindOpponent() {
        return this.player.at() < this.opponent.at();
    }

    public boolean isAheadOfOpponent() {
        return this.player.at() > this.opponent.at();
    }

    public RelativePosition getOpponentRelativePosition() {
        int posDiff = this.opponent.at() - this.player.at();
        switch (posDiff) {
            case -2:
                return RelativePosition.FAR_LEFT;
            case -1:
                return RelativePosition.LEFT;
            case 0:
                return RelativePosition.PAR;
            case 1:
                return RelativePosition.RIGHT;
            case 2:
                return RelativePosition.FAR_RIGHT;
            default:
                return RelativePosition.ACROSS;
        }
    }

    public boolean isWithinTrappingRange() {
        return this.player.at() - this.opponent.at()
                >= this.opponent.getAcceleratedSpeed();
    }

    public Position getCyberTruckDeployPosition() {
        if (this.willOpponentHitObstacleIf(GoAction.NORMAL)) {
            return new Position(this.opponent.lane(),
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnLeft()
                && this.willOpponentHitObstacleIf(GoAction.TURN_LEFT)
        ) {
            return new Position(this.opponent.lane() - 1,
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnRight()
                && this.willOpponentHitObstacleIf(GoAction.TURN_RIGHT)
        ) {
            return new Position(this.opponent.lane() + 1,
                    this.opponent.at() + 1);
        } else if (this.willOpponentHitHardObstacleIf(GoAction.NORMAL)) {
            return new Position(this.opponent.lane(),
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnLeft()
                && this.willOpponentHitHardObstacleIf(GoAction.TURN_LEFT)
        ) {
            return new Position(this.opponent.lane() - 1,
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnRight()
                && this.willOpponentHitHardObstacleIf(GoAction.TURN_RIGHT)
        ) {
            return new Position(this.opponent.lane() + 1,
                    this.opponent.at() + 1);
        }
        return new Position(this.opponent.lane(),
                this.opponent.at() + 1);
    }

    public PowerUpSearchResult searchPowerUp(
            PowerUp powerUp,
            boolean forced
    ) {
        boolean found = false;
        GoAction actionToDo = GoAction.NORMAL;
        actions_iteration:
        for (GoAction action : Bot.GO_ACTIONS) {
//            Guard
            if ((action == GoAction.TURN_LEFT && !this.canTurnLeft())
                    || (action == GoAction.TURN_RIGHT && !this.canTurnRight())) {
                continue;
            }
            GoActionParams params = this.calcGoingParamsFor(
                    Racer.SELF,
                    action
            );

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
        for (GoAction action : Bot.GO_ACTIONS_REVERSED) {
//            Guard
            if ((action == GoAction.TURN_LEFT && !this.canTurnLeft())
                    || (action == GoAction.TURN_RIGHT && !this.canTurnRight())) {
                continue;
            }

            Car car = this.player.clone();

            GoActionParams params = this.calcGoingParamsFor(
                    Racer.SELF,
                    action
            );

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

    private void calcBlockade() {
        this.leftBlocked = !this.canTurnLeft()
                || this.willHitObstacleIf(GoAction.TURN_LEFT);
        this.rightBlocked = !this.canTurnRight()
                || this.willHitObstacleIf(GoAction.TURN_RIGHT);
        this.accBlocked = this.willHitObstacleIf(GoAction.ACCELERATE);
        this.straightBlocked = this.willHitObstacleIf(GoAction.NORMAL);
        this.leftHardBlocked = !this.canTurnLeft()
                || this.willHitHardObstacleIf(GoAction.TURN_LEFT);
        this.rightHardBlocked = !this.canTurnRight()
                || this.willHitHardObstacleIf(GoAction.TURN_RIGHT);
        this.accHardBlocked = this.willHitHardObstacleIf(GoAction.ACCELERATE);
        this.straightHardBlocked = this.willHitHardObstacleIf(GoAction.NORMAL);
    }

    public boolean canSeeObstaclesOnAllLanes(boolean withAcceleration) {
        return this.leftBlocked && this.rightBlocked && (
                withAcceleration ? this.accBlocked : this.straightBlocked
        );
    }

    public boolean canSeeHardObstaclesOnAllLanes(boolean withAcceleration) {
        return this.leftHardBlocked && this.rightHardBlocked && (
                withAcceleration ? this.accHardBlocked : this.straightHardBlocked
        );
    }

    public boolean isStraightBlocked() {
        return this.straightBlocked;
    }

    public boolean isLeftBlocked() {
        return this.leftBlocked || !this.canTurnLeft();
    }

    public boolean isRightBlocked() {
        return this.rightBlocked || !this.canTurnRight();
    }

    public boolean isAccBlocked() {
        return this.accBlocked;
    }

    public boolean isLeftHardBlocked() {
        return this.leftHardBlocked || !this.canTurnLeft();
    }

    public boolean isRightHardBlocked() {
        return this.rightHardBlocked || !this.canTurnRight();
    }

    public boolean isStraightHardBlocked() {
        return this.straightHardBlocked;
    }

    public boolean isAccHardBlocked() {
        return this.accHardBlocked;
    }

    public boolean isNextToHardObstacle() {
        Block block = this.map.getBlock(this.player.lane(),
                this.player.at() + 1);
        return block.isHardObstacle();
    }

    public boolean canSeeHardObstaclesLeftSide() {
        if (this.player.isInLeftmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.player.lane() - 1,
                this.player.at());
        return block.isHardObstacle();
    }

    public boolean canSeeHardObstaclesRightSide() {
        if (this.player.isInRightmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.player.lane() + 1,
                this.player.at());
        return block.isHardObstacle();
    }

    public boolean willBeSafeAfterLizardUses() {
        Block block = this.map.getBlock(this.player.lane(),
                this.player.at() + this.player.speed);
        return block == null || !block.terrain.isObstacle();
    }

    public boolean isHitByEMP() {
        return this.player.is(State.HIT_EMP);
    }

    public boolean stillHasBoost() {
        return this.player.isBoosting();
    }

    public boolean willReachFinishIf(GoAction action) {
        if ((action == GoAction.TURN_LEFT && !this.canTurnLeft())
                || (action == GoAction.TURN_RIGHT && !this.canTurnRight())) {
            return false;
        }

        Car car = this.player.clone();

        GoActionParams params = this.calcGoingParamsFor(
                Racer.SELF,
                action
        );

        for (int i = 0; i < params.diff; i++) {
            int dist = params.start + i;
            Block block = this.map.getBlock(params.lane, dist);
//                Guard
            if (block == null) {
                return false;
            }
            if (block.isSoftObstacle()) {
                int speed = car.speed;
                int newSpeed = car.decelerate();
                params.diff -= speed - newSpeed;
            }

            if (block.isFinishLine()) {
                return true;
            }

            if (block.isHardObstacle()) {
                return false;
            }
        }

        return false;
    }

}

class GoActionParams {
    int diff;
    int start;
    int lane;

    GoActionParams(int start, int lane, int diff) {
        this.start = start;
        this.lane = lane;
        this.diff = diff;
    }

    public String toString() {
        return "Start : " + this.start +
                "\nLane : " + this.lane +
                "\nDiff : " + this.diff;
    }
}

enum Racer {
    SELF,
    OPPONENT
}