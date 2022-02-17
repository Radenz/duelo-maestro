package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.Detector;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.entities.Position;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.ObstacleType;
import za.co.entelect.challenge.type.RelativePosition;

/**
 * {@code PositionDetector} is a detector to detect and
 * calculate logic process related to player's and opponent's
 * cars.
 */
public class PositionDetector extends Detector {
    private Car opponent;
    private ObstacleDetector selfObstacleDetector;
    private ObstacleDetector opponentObstacleDetector;

    /**
     * Creates a new {@code PositionDetector}.
     * @param self player's car
     * @param opponent opponent's car
     * @param map current local map
     * @param selfObstacleDetector player's {@code ObstacleDetector}
     * @param opponentObstacleDetector opponent's {@code ObstacleDetector}
     */
    public PositionDetector(
            Car self,
            Car opponent,
            LocalMap map,
            ObstacleDetector selfObstacleDetector,
            ObstacleDetector opponentObstacleDetector
    ) {
        this.car = self;
        this.opponent = opponent;
        this.map = map;
        this.selfObstacleDetector = selfObstacleDetector;
        this.opponentObstacleDetector = opponentObstacleDetector;
    }

    /**
     * Checks if player's car is behind opponent's car.
     * @return true if player's car is behind opponent's car,
     *         false otherwise
     */
    public boolean isBehindOpponent() {
        return this.car.at() < this.opponent.at();
    }

    /**
     * Checks if player's car is ahead of opponent's car.
     * @return true if player's car is ahead of opponent's car,
     *         false otherwise
     */
    public boolean isAheadOfOpponent() {
        return this.car.at() > this.opponent.at();
    }

    /**
     * Calculates opponent's car relative position to
     * player's car.
     * @return opponent's car relative position to
     *         player's car
     */
    public RelativePosition getOpponentRelativePosition() {
        int posDiff = this.opponent.at() - this.car.at();
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
     * Calculates if opponent's position is far enough
     * behind for player to use oil trap.
     * @return true if opponent's position is far enough
     *         behind to trap
     */
    public boolean isWithinTrappingRange() {
        return this.car.at() - this.opponent.at()
                >= this.opponent.getAcceleratedSpeed();
    }

    /**
     * Chooses best position to deploy cyber truck based
     * on surrounding obstacles relative to the opponent's car.
     * @version 2
     * @return best possible cyber truck deploy
     *         position
     */
    public Position getCyberTruckDeployPosition() {
        if (this.opponentObstacleDetector
                .willHit(ObstacleType.ALL)
                .when(GoAction.NORMAL)) {
            return new Position(this.opponent.lane(),
                    this.opponent.at() + this.opponent.speed + 1);
        } else if (this.opponent.canTurnLeft()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.ALL)
                .when(GoAction.TURN_LEFT)
        ) {
            return new Position(this.opponent.lane() - 1,
                    this.opponent.at() + this.opponent.speed);
        } else if (this.opponent.canTurnRight()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.ALL)
                .when(GoAction.TURN_RIGHT)
        ) {
            return new Position(this.opponent.lane() + 1,
                    this.opponent.at()  + this.opponent.speed);
        } else if (this.opponentObstacleDetector
                .willHit(ObstacleType.HARD)
                .when(GoAction.NORMAL)) {
            return new Position(this.opponent.lane(),
                    this.opponent.at() + this.opponent.speed + 1);
        } else if (this.opponent.canTurnLeft()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.HARD)
                .when(GoAction.TURN_LEFT)
        ) {
            return new Position(this.opponent.lane() - 1,
                    this.opponent.at()  + this.opponent.speed);
        } else if (this.opponent.canTurnRight()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.HARD)
                .when(GoAction.TURN_RIGHT)
        ) {
            return new Position(this.opponent.lane() + 1,
                    this.opponent.at() + this.opponent.speed);
        }
        return new Position(this.opponent.lane(),
                this.opponent.at() + this.opponent.speed + 1);
    }

    /**
     * Calculates if player will land on safe block
     * after using lizard power up.
     * @return true if player will land safely after
     *         using lizard
     */
    public boolean willBeSafeAfterLizardUses() {
        Block block = this.map.getBlock(this.car.lane(),
                this.car.at() + this.car.speed);
        return block == null || !block.terrain.isObstacle();
    }
}
