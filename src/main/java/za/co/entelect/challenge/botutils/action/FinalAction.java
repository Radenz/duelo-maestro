package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;

public enum FinalAction implements Action {
    ACCELERATE,
    NORMAL,
    TURN_RIGHT,
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
