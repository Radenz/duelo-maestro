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
    private Strategy strategy = Strategy.HASTE_SKIPPER;

    private ObstacleDetector selfObstacleDetector;
    private ObstacleDetector opponentObstacleDetector;
    private PowerUpDetector powerUpDetector;
    private PositionDetector positionDetector;

    private HashMap<Class, Checker> checkers;

    /**
     * Creates a new bot from specific game state.
     * @param gameState current state of the game.
     */
    public Bot(GameState gameState) {
        this.player = gameState.player;
        this.opponent = gameState.opponent;
        this.map = LocalMap.from(gameState);

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

    /**
     * Switch strategy to specified strategy. All {@code Bot}
     * use {@code HASTE_SKIPPER} strategy by default.
     * @param strategy strategy to use
     */
    public void useStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Analyze game state and calculate the best feasible action
     * to do at the current state based on the {@code Action} list
     * of the strategy.
     * @return command to execute to the game
     */
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

}