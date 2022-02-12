package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.FixCommand;
import za.co.entelect.challenge.entities.Car;

/**
 * {@code FinalAction} contains various actions to fix
 * player's car based on its damage.
 */
public enum FixAction implements Action {
    /**
     * Urgent fix action, feasible if the player car is
     * totalled, i.e. having the maximum damage (5).
     */
    URGENT,
    /**
     * Semi urgent fix action, feasible if the player car is
     * halfway to totalled, i.e. having 3 or more damage.
     */
    SEMI_URGENT,
    /**
     * Normal fix action, always feasible.
     */
    NORMAL;

    /**
     * @deprecated
     */
    public boolean isFeasibleFor(Bot bot) {
        Car player = bot.player();
        switch (this) {
            case URGENT:
                return player.damage >= 5;
            case SEMI_URGENT:
                return player.damage >= 3;
            case NORMAL:
            default:
                return true;
        }
    }

    public Command execute() {
        return new FixCommand();
    }
}
