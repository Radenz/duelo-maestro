package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.type.PowerUpSearchResult;


/**
 * {@code GoAction} contains various actions to
 * pick up nearby power ups.
 */
public enum PickUpAction implements Action {
    /**
     * Pick EMP action. Only feasible if there is reachable
     * nearby EMP power up that doesn't require player's car
     * to hit an obstacle to reach.
     */
    EMP,
    /**
     * Pick TWEET action. Only feasible if there is reachable
     * nearby TWEET power up that doesn't require player's car
     * to hit an obstacle to reach.
     */
    CYBERTRUCK,
    /**
     * Pick BOOST action. Only feasible if there is reachable
     * nearby BOOST power up that doesn't require player's car
     * to hit an obstacle to reach.
     */
    BOOST,
    /**
     * Pick OIL action. Only feasible if there is reachable
     * nearby OIL power up that doesn't require player's car
     * to hit an obstacle to reach.
     */
    OIL,
    /**
     * Pick LIZARD action. Only feasible if there is reachable
     * nearby LIZARD power up that doesn't require player's car
     * to hit an obstacle to reach.
     */
    LIZARD,
    /**
     * Forced pick EMP action. Only feasible if there is reachable
     * nearby EMP power up that is not blocked by a hard obstacle.
     */
    EMP_FORCE,
    /**
     * Forced pick TWEET action. Only feasible if there is reachable
     * nearby TWEET power up that is not blocked by a hard obstacle.
     */
    CYBERTRUCK_FORCE,
    /**
     * Forced pick BOOST action. Only feasible if there is reachable
     * nearby BOOST power up that is not blocked by a hard obstacle.
     */
    BOOST_FORCE,
    /**
     * Forced pick OIL action. Only feasible if there is reachable
     * nearby OIL power up that is not blocked by a hard obstacle.
     */
    OIL_FORCE,
    /**
     * Forced pick LIZARD action. Only feasible if there is reachable
     * nearby LIZARD power up that is not blocked by a hard obstacle.
     */
    LIZARD_FORCE;

    /**
     * @deprecated
     */
    private GoAction action;

    /**
     * @deprecated
     */
    public boolean isFeasibleFor(Bot bot) {
        boolean forced = this.isForced();
        PowerUp powerUp = this.toPowerUp();

        PowerUpSearchResult result = bot.searchPowerUp(
                powerUp,
                forced
        );

        this.action = result.action;
        return result.available;
    }

    /**
     * Returns true if the {@code PickUpAction} is forced.
     * @return true if the {@code PickUpAction} is forced,
     *         false otherwise
     */
    public boolean isForced() {
        switch (this) {
            case EMP_FORCE:
            case CYBERTRUCK_FORCE:
            case BOOST_FORCE:
            case OIL_FORCE:
            case LIZARD_FORCE:
                return true;
        }
        return false;
    }

    /**
     * Maps the {@code PickUpAction} to its corresponding
     * {@code PowerUp}.
     * @return the {@code PowerUp} that corresponds the
     *         {@code PickUpAction}
     */
    public PowerUp toPowerUp() {
        switch (this) {
            case EMP:
            case EMP_FORCE:
                return PowerUp.EMP;
            case CYBERTRUCK:
            case CYBERTRUCK_FORCE:
                return PowerUp.TWEET;
            case BOOST:
            case BOOST_FORCE:
                return PowerUp.BOOST;
            case OIL:
            case OIL_FORCE:
                return PowerUp.OIL;
            case LIZARD:
            case LIZARD_FORCE:
            default:
                return PowerUp.LIZARD;
        }
    }

    public Command execute() {
        /**
         * @deprecated
         */
        return this.action.execute();
    }

}
