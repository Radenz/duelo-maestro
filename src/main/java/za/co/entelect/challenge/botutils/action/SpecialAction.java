package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.DoNothingCommand;

/**
 * {@code SpecialAction} contains special actions that
 * is not defined by any other action category.
 */
public enum SpecialAction implements Action {
    /**
     * Berserk action. Force-use any owned power up.
     * Only feasible if either player's car or opponent's
     * car has reached the last fifth of the race.
     */
    BERSERK,
    /**
     * Ultra berserk action. Force-use any owned power up.
     * Only feasible if either player's car or opponent's
     * car has reached the last half of the race.
     */
    ULTRA_BERSERK,
    /**
     * Greedy pick up action. Force-pickup any nearest power
     * ups. Will get as many power up as possible and
     * decelerate the car if needed.
     */
    GREEDY_PICK_UP,
    /**
     * Rage action. Force-use EMP or TWEET power ups if the
     * player has any. Always feasible on {@code RAGE} strategy
     * if the player has a TWEET power up or has an EMP power
     * up and reasonable to use the EMP.
     */
    RAGE,
    /**
     * EMP combo action. Force-use EMP repeatedly. Only feasible
     * if the player has at least 5 EMPs or has used EMP in the
     * last round.
     */
    EMP_COMBO;

    public Command execute() {
        return new DoNothingCommand();
    }

}
