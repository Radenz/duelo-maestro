package za.co.entelect.challenge.botutils;

import za.co.entelect.challenge.botutils.action.Action;
import za.co.entelect.challenge.botutils.detector.ObstacleDetector;
import za.co.entelect.challenge.botutils.detector.PositionDetector;
import za.co.entelect.challenge.botutils.detector.PowerUpDetector;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.entities.Car;

/**
 * {@code Checker} is an abstract action checker
 * that checks an action using {@code ObstacleDetector},
 * {@code PowerUpDetector}, {@code PositionDetector},
 * and both player's {@code Car} information.
 */
public abstract class Checker {
    /**
     * Action to check.
     */
    protected Action action;
    /**
     * A detector to detect obstacles in player's
     * surrounding.
     */
    protected ObstacleDetector obstacleDetector;
    /**
     * A detector to detect power ups in player's
     * surrounding.
     */
    protected PowerUpDetector powerUpDetector;
    /**
     * A detector to detect and calculate player
     * and opponent position.
     */
    protected PositionDetector positionDetector;
    /**
     * Player's {@code Car}.
     */
    protected Car self;
    /**
     * Opponent's {@code Car}.
     */
    protected Car opponent;
    /**
     * Action to execute as a command mapped
     * from the action to check.
     */
    protected Action actionToExecute;

    /**
     * Creates a new checker based on player's and
     * opponent's cars.
     * @param self player's car
     * @param opponent opponent's car
     */
    public Checker(Car self, Car opponent) {
        this.self = self;
        this.opponent = opponent;
    }

    /**
     * Attaches a detector to the checker to use.
     * @param detector detector to attach
     */
    public void use(Detector detector) {
        if (detector instanceof ObstacleDetector) {
            this.obstacleDetector = (ObstacleDetector) detector;
        } else if (detector instanceof PowerUpDetector) {
            this.powerUpDetector = (PowerUpDetector) detector;
        } else if (detector instanceof PositionDetector) {
            this.positionDetector = (PositionDetector) detector;
        }
    }

    /**
     * Attaches an action to the {@code Checker} to check.
     * @param action action to check
     * @return this {@code Checker} containing the action
     */
    public abstract Checker is(Action action);

    /**
     * Checks whether the action to check is feasible or not.
     * Must be called only after calling {@code is()} method.
     * @return true if the action to check is feasible, false
     *         otherwise
     */
    public abstract boolean feasible();

    /**
     * Executes the {@code actionToDo} based on checked
     * feasible action. Must be called only after calling
     * {@code feasible()} method, otherwise may perform
     * an unexpected behavior.
     * @return the command to execute in the game
     */
    public Command execute() {
        return actionToExecute.execute();
    }

}
