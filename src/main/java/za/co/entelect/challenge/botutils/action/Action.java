package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;

/**
 * {@code Action} represents various actions the
 * bot can check the feasibility of and execute.
 */
public interface Action {
    /**
     * @deprecated
     */
    boolean isFeasibleFor(Bot bot);

    /**
     * Executes the action.
     * @return a command to return to the game
     */
    Command execute();
}
