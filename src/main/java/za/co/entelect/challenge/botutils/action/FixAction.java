package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.FixCommand;

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

    public Command execute() {
        return new FixCommand();
    }
}
