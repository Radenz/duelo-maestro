package za.co.entelect.challenge.botutils;

import za.co.entelect.challenge.botutils.action.*;

/**
 * Enum {@code Strategy} contains all strategies implemented
 * within Duelo Maestro bot.
 */
public enum Strategy {
    /**
     * {@code SAFE} strategy picks & uses power ups wisely.
     * This strategy prefers using power ups than picking them,
     * and prefers picking power ups than accelerating or
     * randomly switch lane without searching for power ups.
     */
    SAFE,
    /**
     * {@code BERSERK} strategy piles up some power ups and
     * uses them repeatedly at the last fifth of the race,
     * known as the {@code BERSERK} action.
     */
    BERSERK,
    /**
     * {@code RAGE} strategy damages opponent car as often
     * as possible. This strategy prefers picking up {@code EMP}
     * and {@code TWEET} power ups among the others. This
     * strategy also uses other power ups wisely.
     */
    RAGE,
    /**
     * {@code BOOST_MANIAC} strategy behaves similarly as
     * {@code SAFE} strategy, but uses & picks up boost power
     * ups as often as possible.
     */
    BOOST_MANIAC,
    /**
     * {@code POWER_UP_MANIAC} strategy uses and picks up any
     * power up as often as possible.
     */
    POWER_UP_MANIAC,
    /**
     * {@code ULTRA_BERSERK} strategy behaves similarly as
     * {@code BERSERK} strategy, but picks up power ups more
     * often and berserk at the last half of the race instead
     * of the last fifth.
     */
    ULTRA_BERSERK,
    /**
     * {@code HASTE_BERSERK} strategy behaves similarly as
     * {@code BERSERK} strategy, but uses boosts as soon as
     * the player have them instead of waiting until the last
     * fifth of the race.
     */
    HASTE_BERSERK,
    /**
     * {@code DESTROYER} strategy piles up as many {@code EMP}
     * as possible, then use it repeatedly to destroy opponent's
     * car and restrict them to switch lane.
     */
    DESTROYER,
    /**
     * {@code SKIPPER} strategy behaves similarly as {@code SAFE}
     * strategy, but prefers lizard among any other power ups.
     */
    SKIPPER,
    /**
     * {@code HASTE_SKIPPER} strategy behaves similarly as
     * {@code SKIPPER} strategy, but prefers to boost instead
     * of using lizard.
     */
    HASTE_SKIPPER;

    /**
     * Get a list of {@code Action} of specific strategy sorted
     * by priority from highest to lowest.
     * @param strategy strategy
     * @return a list of actions that corresponds the strategy
     */
    public static Action[] getActions(Strategy strategy) {
        switch (strategy) {
            case SAFE:
                return Strategy.getSafeStrategyActions();
            case BERSERK:
                return Strategy.getBerserkStrategyActions();
            case RAGE:
                return Strategy.getRageStrategyActions();
            case BOOST_MANIAC:
                return Strategy.getBoostManiacStrategyActions();
            case POWER_UP_MANIAC:
                return Strategy.getPowerUpManiacStrategyActions();
            case ULTRA_BERSERK:
                return Strategy.getUltraBerserkStrategyActions();
            case HASTE_BERSERK:
                return Strategy.getHasteBerserkStrategyActions();
            case DESTROYER:
                return Strategy.getDestroyerStrategyActions();
            case SKIPPER:
                return Strategy.getSkipperStrategyActions();
            case HASTE_SKIPPER:
            default:
                return Strategy.getHasteSkipperStrategyActions();
        }
    }

    private static Action[] getSafeStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                UseAction.EMP,
                UseAction.CYBERTRUCK,
                UseAction.BOOST,
                UseAction.OIL,
                UseAction.LIZARD,
                FixAction.SEMI_URGENT,
                PickUpAction.EMP,
                PickUpAction.CYBERTRUCK,
                PickUpAction.BOOST,
                PickUpAction.OIL,
                PickUpAction.LIZARD,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getBerserkStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                SpecialAction.BERSERK,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                PickUpAction.BOOST_FORCE,
                PickUpAction.OIL_FORCE,
                PickUpAction.LIZARD_FORCE,
                FixAction.SEMI_URGENT,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getRageStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                SpecialAction.RAGE,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                UseAction.EMP_FORCE,
                UseAction.CYBERTRUCK_FORCE,
                PickUpAction.BOOST,
                PickUpAction.OIL,
                PickUpAction.LIZARD,
                UseAction.BOOST,
                UseAction.OIL,
                UseAction.LIZARD,
                FixAction.SEMI_URGENT,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getBoostManiacStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                PickUpAction.BOOST_FORCE,
                UseAction.BOOST_FORCE,
                UseAction.EMP,
                UseAction.CYBERTRUCK,
                UseAction.OIL,
                UseAction.LIZARD,
                FixAction.SEMI_URGENT,
                PickUpAction.EMP,
                PickUpAction.CYBERTRUCK,
                PickUpAction.OIL,
                PickUpAction.LIZARD,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getPowerUpManiacStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                UseAction.EMP_FORCE,
                UseAction.CYBERTRUCK_FORCE,
                UseAction.BOOST_FORCE,
                UseAction.OIL_FORCE,
                UseAction.LIZARD_FORCE,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                PickUpAction.BOOST_FORCE,
                PickUpAction.OIL_FORCE,
                PickUpAction.LIZARD_FORCE,
                FixAction.SEMI_URGENT,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getUltraBerserkStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                SpecialAction.ULTRA_BERSERK,
                SpecialAction.GREEDY_PICK_UP,
                FixAction.SEMI_URGENT,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getHasteBerserkStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                UseAction.BOOST_FORCE,
                SpecialAction.BERSERK,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                PickUpAction.BOOST_FORCE,
                PickUpAction.OIL_FORCE,
                PickUpAction.LIZARD_FORCE,
                FixAction.SEMI_URGENT,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getDestroyerStrategyActions() {
        return new Action[]{
                SpecialAction.EMP_COMBO,
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                PickUpAction.EMP_FORCE,
                UseAction.CYBERTRUCK,
                UseAction.BOOST,
                UseAction.OIL,
                UseAction.LIZARD,
                FixAction.SEMI_URGENT,
                PickUpAction.CYBERTRUCK,
                PickUpAction.OIL,
                PickUpAction.LIZARD,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    /**
     * @version 2
     * @return {@code SKIPPER} strategy actions.
     */
    private static Action[] getSkipperStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                UseAction.LIZARD,
                UseAction.EMP,
                UseAction.CYBERTRUCK,
                UseAction.BOOST,
                UseAction.OIL,
                FixAction.SEMI_URGENT,
                PickUpAction.LIZARD,
                PickUpAction.EMP,
                PickUpAction.CYBERTRUCK,
                PickUpAction.BOOST,
                PickUpAction.OIL,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }

    private static Action[] getHasteSkipperStrategyActions() {
        return new Action[]{
                FinalAction.ACCELERATE,
                FinalAction.NORMAL,
                FinalAction.TURN_LEFT,
                FinalAction.TURN_RIGHT,
                FixAction.URGENT,
                UseAction.BOOST,
                UseAction.LIZARD,
                UseAction.EMP,
                UseAction.CYBERTRUCK,
                UseAction.OIL,
                FixAction.SEMI_URGENT,
                PickUpAction.LIZARD,
                PickUpAction.EMP,
                PickUpAction.CYBERTRUCK,
                PickUpAction.BOOST,
                PickUpAction.OIL,
                GoAction.ACCELERATE,
                GoAction.NORMAL,
                GoAction.TURN_LEFT,
                GoAction.TURN_RIGHT,
                GoAction.DECELERATE,
                FixAction.NORMAL
        };
    }
}
