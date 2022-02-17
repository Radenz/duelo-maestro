package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.entities.Position;
import za.co.entelect.challenge.enums.PowerUp;

/**
 * {@code UseAction} contains various actions to
 * use owned power ups.
 */
public enum UseAction implements Action {
    /**
     * Use EMP action. Only feasible if the player has
     * an EMP power up, will not hit hard obstacles on
     * using it, is behind the opponent, and has the
     * opponent within the EMP range.
     */
    EMP,
    /**
     * Use TWEET action. Only feasible if the player has
     * a TWEET power up and will not hit hard obstacles on
     * using it.
     */
    CYBERTRUCK,
    /**
     * Use BOOST action. Only feasible if the player has
     * a BOOST power up, will not hit hard obstacles on
     * using it, and is not currently boosting.
     */
    BOOST,
    /**
     * Use OIL action. Only feasible if the player has
     * an OIL power up, will not hit hard obstacles on
     * using it, and is ahead of opponent.
     */
    OIL,
    /**
     * Use LIZARD action. Only feasible if the player has
     * a LIZARD power up, will not hit hard obstacles on
     * using it, has obstacles ahead, and will not land
     * on an obstacle after using lizard power up.
     */
    LIZARD,
    /**
     * Force-use EMP action. Similar to use EMP action
     * but ignore hard obstacles.
     */
    EMP_FORCE,
    /**
     * Force-use TWEET action. Similar to use TWEET action
     * but ignore hard obstacles.
     */
    CYBERTRUCK_FORCE,
    /**
     * Force-use BOOST action. Similar to use BOOST action
     * but ignore hard obstacles.
     */
    BOOST_FORCE,
    /**
     * Force-use OIL action. Similar to use OIL action
     * but ignore hard obstacles.
     */
    OIL_FORCE,
    /**
     * Force-use LIZARD action. Similar to use LIZARD action
     * but ignore hard obstacles and landing block.
     */
    LIZARD_FORCE;

    private static Position cyberTruckDeployPosition;

    public Command execute() {
        switch (this) {
            case EMP:
            case EMP_FORCE:
                return new EmpCommand();
            case CYBERTRUCK:
            case CYBERTRUCK_FORCE:
                return new TweetCommand(cyberTruckDeployPosition.lane(),
                        cyberTruckDeployPosition.distance());
            case BOOST:
            case BOOST_FORCE:
                return new BoostCommand();
            case OIL:
            case OIL_FORCE:
                return new OilCommand();
            case LIZARD:
            case LIZARD_FORCE:
            default:
                return new LizardCommand();
        }
    }

    /**
     * Maps the {@code UseAction} to its corresponding
     * {@code PowerUp}.
     * @return a {@code PowerUp} object that corresponds the
     *         {@code UseAction}
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

    public static void setCyberTruckDeployPosition(Position position) {
        UseAction.cyberTruckDeployPosition = position;
    }
}
