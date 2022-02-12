package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;

/**
 * {@code FinalAction} contains various actions that will
 * only be feasible as a final action to execute before
 * reaching finish line.
 */
public enum FinalAction implements Action {
    /**
     * Accelerate final action. Only feasible if accelerating
     * at the current state results in reaching the finish line.
     */
    ACCELERATE,
    /**
     * Do nothing final action. Only feasible if doing nothing
     * at the current state results in reaching the finish line.
     */
    NORMAL,
    /**
     * Switch to right side lane final action. Only feasible if
     * switching to right side lane at the current state results in
     * reaching the finish line.
     */
    TURN_RIGHT,
    /**
     * Switch to left side lane final action. Only feasible if
     * switching to left side lane at the current state results in
     * reaching the finish line.
     */
    TURN_LEFT;

    /**
     * @deprecated
     */
    @Override
    public boolean isFeasibleFor(Bot bot) {
        return bot.willReachFinishIf(this.toGoAction());
    }

    @Override
    public Command execute() {
        return this.toGoAction().execute();
    }

    /**
     * Maps this {@code FinalAction} to its corresponding
     * {@code GoAction}.
     * @return {@code GoAction} that corresponds this {@code FinalAction}
     */
    public GoAction toGoAction() {
        switch (this) {
            case ACCELERATE:
                return GoAction.ACCELERATE;
            case NORMAL:
                return GoAction.NORMAL;
            case TURN_RIGHT:
                return GoAction.TURN_RIGHT;
            case TURN_LEFT:
            default:
                return GoAction.TURN_LEFT;
        }
    }
}
