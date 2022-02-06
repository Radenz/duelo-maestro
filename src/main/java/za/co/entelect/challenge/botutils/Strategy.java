package za.co.entelect.challenge.botutils;

import za.co.entelect.challenge.botutils.action.*;

public enum Strategy {
    SAFE,
    BERSERK,
    RAGE,
    BOOST_MANIAC,
    POWER_UP_MANIAC,
    ULTRA_BERSERK,
    HASTE_BERSERK,
    DESTROYER,
    SKIPPER;

    public static Action[] getActions(Strategy strat) {
        switch (strat) {
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
            default:
                return Strategy.getSkipperStrategyActions();
        }
    }

    private static Action[] getSafeStrategyActions() {
        return new Action[]{
                FixAction.URGENT,
//                EscapeAction.EMP,
//                EscapeAction.CYBERTRUCK,
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
                FixAction.URGENT,
                SpecialAction.BERSERK,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                PickUpAction.BOOST_FORCE,
                PickUpAction.OIL_FORCE,
                PickUpAction.LIZARD_FORCE,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
                FixAction.URGENT,
                SpecialAction.RAGE,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                UseAction.EMP_FORCE,
                UseAction.CYBERTRUCK_FORCE,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
                FixAction.URGENT,
                PickUpAction.BOOST_FORCE,
                UseAction.BOOST_FORCE,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
                FixAction.URGENT,
                SpecialAction.ULTRA_BERSERK,
                SpecialAction.GREEDY_PICK_UP,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
                FixAction.URGENT,
                UseAction.BOOST_FORCE,
                SpecialAction.BERSERK,
                PickUpAction.EMP_FORCE,
                PickUpAction.CYBERTRUCK_FORCE,
                PickUpAction.BOOST_FORCE,
                PickUpAction.OIL_FORCE,
                PickUpAction.LIZARD_FORCE,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
                FixAction.URGENT,
                PickUpAction.EMP_FORCE,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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

    private static Action[] getSkipperStrategyActions() {
        return new Action[]{
                FixAction.URGENT,
                UseAction.LIZARD_FORCE,
                EscapeAction.EMP,
                EscapeAction.CYBERTRUCK,
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
}
