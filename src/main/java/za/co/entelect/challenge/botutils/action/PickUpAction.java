package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.type.PowerUpSearchResult;

public enum PickUpAction implements Action {
    EMP,
    CYBERTRUCK,
    BOOST,
    OIL,
    LIZARD,
    EMP_FORCE,
    CYBERTRUCK_FORCE,
    BOOST_FORCE,
    OIL_FORCE,
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
