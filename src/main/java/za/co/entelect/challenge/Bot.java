package za.co.entelect.challenge;

import za.co.entelect.challenge.botutils.Checker;
import za.co.entelect.challenge.botutils.Strategy;
import za.co.entelect.challenge.botutils.action.*;
import za.co.entelect.challenge.botutils.checker.*;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.botutils.detector.PositionDetector;
import za.co.entelect.challenge.botutils.detector.PowerUpDetector;
import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.entities.*;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.enums.State;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.PowerUpSearchResult;
import za.co.entelect.challenge.type.RelativePosition;

import java.util.HashMap;


public class Bot {

    private Car opponent;
    private Car player;

    private LocalMap map;
    private Strategy strategy = Strategy.SAFE;

    private ObstacleDetector selfObstacleDetector;
    private ObstacleDetector opponentObstacleDetector;
    private PowerUpDetector powerUpDetector;
    private PositionDetector positionDetector;

    private HashMap<Class, Checker> checkers;

    /**
     * @deprecated
     */
    private static final GoAction[] GO_ACTIONS = {
            GoAction.ACCELERATE,
            GoAction.NORMAL,
            GoAction.TURN_LEFT,
            GoAction.TURN_RIGHT,
            GoAction.DECELERATE,
    };

    /**
     * @deprecated
     */
    private static final GoAction[] GO_ACTIONS_REVERSED = {
            GoAction.DECELERATE,
            GoAction.TURN_RIGHT,
            GoAction.TURN_LEFT,
            GoAction.NORMAL,
            GoAction.ACCELERATE,
    };

    /**
     * @deprecated
     */
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

        this.selfObstacleDetector = new ObstacleDetector(
                this.player,
                this.map
        );

        this.opponentObstacleDetector = new ObstacleDetector(
                this.opponent,
                this.map
        );

        this.powerUpDetector = new PowerUpDetector(
                this.player,
                this.map
        );

        this.positionDetector = new PositionDetector(
                this.player,
                this.opponent,
                this.map,
                this.selfObstacleDetector,
                this.opponentObstacleDetector
        );

        UseAction.setCyberTruckDeployPosition(
                positionDetector.getCyberTruckDeployPosition()
        );

        this.checkers = new HashMap<>();
        this.checkers.put(GoAction.class, new GoActionChecker(this.player, this.opponent));
        this.checkers.put(FixAction.class, new FixActionChecker(this.player, this.opponent));
        this.checkers.put(PickUpAction.class, new PickUpActionChecker(this.player, this.opponent));
        this.checkers.put(SpecialAction.class, new SpecialActionChecker(this.player, this.opponent));
        this.checkers.put(UseAction.class, new UseActionChecker(this.player, this.opponent));
        this.checkers.put(FinalAction.class, new FinalActionChecker(this.player, this.opponent));
    }

    public void useStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Command run() {

        Action[] actions = Strategy.getActions(this.strategy);
        for (Action action : actions) {
            Checker checker = this.checkers.get(action.getClass());
            checker.use(this.selfObstacleDetector);
            checker.use(this.powerUpDetector);
            checker.use(this.positionDetector);
            if (checker.is(action).feasible()) {
                return checker.execute();
            }
        }
        return new DoNothingCommand();
    }

    /**
     * @deprecated
     */
    public Car player() {
        return this.player;
    }

    /**
     * @deprecated
     */
    public Car opponent() {
        return this.opponent;
    }

    /**
     * @deprecated
     */
    public boolean canTurnLeft() {
        return this.player.canTurnLeft();
    }

    /**
     * @deprecated
     */
    public boolean canTurnRight() {
        return this.player.canTurnRight();
    }

    /**
     * @deprecated
     */
    public boolean canGainMoreSpeed() {
        return this.player.canGainMoreSpeed();
    }

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
    public boolean isBehindOpponent() {
        return this.player.at() < this.opponent.at();
    }

    /**
     * @deprecated
     */
    public boolean isAheadOfOpponent() {
        return this.player.at() > this.opponent.at();
    }

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
    public boolean isWithinTrappingRange() {
        return this.player.at() - this.opponent.at()
                >= this.opponent.getAcceleratedSpeed();
    }

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
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

    /**
     * @deprecated
     */
    public boolean canSeeObstaclesOnAllLanes(boolean withAcceleration) {
        return this.leftBlocked && this.rightBlocked && (
                withAcceleration ? this.accBlocked : this.straightBlocked
        );
    }

    /**
     * @deprecated
     */
    public boolean canSeeHardObstaclesOnAllLanes(boolean withAcceleration) {
        return this.leftHardBlocked && this.rightHardBlocked && (
                withAcceleration ? this.accHardBlocked : this.straightHardBlocked
        );
    }

    /**
     * @deprecated
     */
    public boolean isStraightBlocked() {
        return this.straightBlocked;
    }

    /**
     * @deprecated
     */
    public boolean isLeftBlocked() {
        return this.leftBlocked || !this.canTurnLeft();
    }

    /**
     * @deprecated
     */
    public boolean isRightBlocked() {
        return this.rightBlocked || !this.canTurnRight();
    }

    /**
     * @deprecated
     */
    public boolean isAccBlocked() {
        return this.accBlocked;
    }

    /**
     * @deprecated
     */
    public boolean isLeftHardBlocked() {
        return this.leftHardBlocked || !this.canTurnLeft();
    }

    /**
     * @deprecated
     */
    public boolean isRightHardBlocked() {
        return this.rightHardBlocked || !this.canTurnRight();
    }

    /**
     * @deprecated
     */
    public boolean isStraightHardBlocked() {
        return this.straightHardBlocked;
    }

    /**
     * @deprecated
     */
    public boolean isAccHardBlocked() {
        return this.accHardBlocked;
    }

    /**
     * @deprecated
     */
    public boolean isNextToHardObstacle() {
        Block block = this.map.getBlock(this.player.lane(),
                this.player.at() + 1);
        return block.isHardObstacle();
    }

    /**
     * @deprecated
     */
    public boolean canSeeHardObstaclesLeftSide() {
        if (this.player.isInLeftmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.player.lane() - 1,
                this.player.at());
        return block.isHardObstacle();
    }

    /**
     * @deprecated
     */
    public boolean canSeeHardObstaclesRightSide() {
        if (this.player.isInRightmostLane()) {
            return false;
        }
        Block block = this.map.getBlock(this.player.lane() + 1,
                this.player.at());
        return block.isHardObstacle();
    }

    /**
     * @deprecated
     */
    public boolean willBeSafeAfterLizardUses() {
        Block block = this.map.getBlock(this.player.lane(),
                this.player.at() + this.player.speed);
        return block == null || !block.terrain.isObstacle();
    }

    /**
     * @deprecated
     */
    public boolean isHitByEMP() {
        return this.player.is(State.HIT_EMP);
    }

    /**
     * @deprecated
     */
    public boolean stillHasBoost() {
        return this.player.isBoosting();
    }

    /**
     * @deprecated
     */
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

/**
 * @deprecated
 */
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

/**
 * @deprecated
 */
enum Racer {
    SELF,
    OPPONENT
}