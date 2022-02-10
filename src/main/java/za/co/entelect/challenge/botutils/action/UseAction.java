package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.entities.Position;
import za.co.entelect.challenge.enums.PowerUp;

public enum UseAction implements Action {
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

    private static Position cyberTruckDeployPosition;

    /**
     * @deprecated
     */
    private Bot bot;

    /**
     * @deprecated
     */
    public boolean isFeasibleFor(Bot bot) {
        this.use(bot);
        boolean willHitObstacle = bot.willHitHardObstacleIf(GoAction.NORMAL);
        if (this == UseAction.EMP_FORCE
                || this == UseAction.CYBERTRUCK_FORCE
                || this == UseAction.BOOST_FORCE
                || this == UseAction.OIL_FORCE
                || this == UseAction.LIZARD_FORCE) {
            willHitObstacle = false;
        }
        boolean feasibility = bot.player().has(this.toPowerUp())
                && !willHitObstacle;
        switch (this) {
            case EMP:
            case EMP_FORCE:
                feasibility = feasibility
                        && bot.getOpponentRelativePosition().
                        isWithinEMPRange()
                        && bot.isBehindOpponent();
                break;

            case BOOST:
                feasibility = feasibility
                        && !bot.willHitHardObstacleIfBoosted()
                        && !bot.stillHasBoost();
                break;
            case OIL:
            case OIL_FORCE:
                feasibility = feasibility
                        && bot.isAheadOfOpponent()
                        && bot.isWithinTrappingRange();
                break;
            case LIZARD:
                feasibility = bot.player().has(this.toPowerUp())
                        && bot.isStraightBlocked()
                        && bot.willBeSafeAfterLizardUses();
                break;
            case LIZARD_FORCE:
                feasibility = bot.player().has(this.toPowerUp())
                        && bot.isStraightBlocked();
                break;

        }
        return feasibility;
    }

    /**
     * @deprecated
     */
    public void use(Bot bot) {
        this.bot = bot;
    }


    public Command execute() {
        switch (this) {
            case EMP:
            case EMP_FORCE:
                return new EmpCommand();
            case CYBERTRUCK:
            case CYBERTRUCK_FORCE:
//                Position deployPos = this.bot.getCyberTruckDeployPosition();
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
