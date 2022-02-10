package za.co.entelect.challenge.botutils.detector;

import za.co.entelect.challenge.botutils.Detector;
import za.co.entelect.challenge.botutils.action.GoAction;
import za.co.entelect.challenge.entities.Block;
import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.entities.Position;
import za.co.entelect.challenge.type.LocalMap;
import za.co.entelect.challenge.type.ObstacleType;
import za.co.entelect.challenge.type.RelativePosition;

public class PositionDetector extends Detector {
    private Car opponent;
    private ObstacleDetector selfObstacleDetector;
    private ObstacleDetector opponentObstacleDetector;

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

    public boolean isBehindOpponent() {
        return this.car.at() < this.opponent.at();
    }

    public boolean isAheadOfOpponent() {
        return this.car.at() > this.opponent.at();
    }

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

    public boolean isWithinEMPTrappingRange() {
        return this.car.at() - this.opponent.at()
                >= this.opponent.getAcceleratedSpeed();
    }

    public Position getCyberTruckDeployPosition() {
        if (this.opponentObstacleDetector
                .willHit(ObstacleType.ALL)
                .when(GoAction.NORMAL)) {
            return new Position(this.opponent.lane(),
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnLeft()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.ALL)
                .when(GoAction.TURN_LEFT)
        ) {
            return new Position(this.opponent.lane() - 1,
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnRight()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.ALL)
                .when(GoAction.TURN_RIGHT)
        ) {
            return new Position(this.opponent.lane() + 1,
                    this.opponent.at() + 1);
        } else if (this.opponentObstacleDetector
                .willHit(ObstacleType.HARD)
                .when(GoAction.NORMAL)) {
            return new Position(this.opponent.lane(),
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnLeft()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.HARD)
                .when(GoAction.TURN_LEFT)
        ) {
            return new Position(this.opponent.lane() - 1,
                    this.opponent.at() + 1);
        } else if (this.opponent.canTurnRight()
                && this.opponentObstacleDetector
                .willHit(ObstacleType.HARD)
                .when(GoAction.TURN_RIGHT)
        ) {
            return new Position(this.opponent.lane() + 1,
                    this.opponent.at() + 1);
        }
        return new Position(this.opponent.lane(),
                this.opponent.at() + 1);
    }

    public boolean willBeSafeAfterLizardUses() {
        Block block = this.map.getBlock(this.car.lane(),
                this.car.at() + this.car.speed);
        return block == null || !block.terrain.isObstacle();
    }
}
