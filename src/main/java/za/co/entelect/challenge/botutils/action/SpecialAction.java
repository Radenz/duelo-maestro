package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.EmpCommand;
import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.enums.State;
import za.co.entelect.challenge.type.PowerUpSearchResult;

public enum SpecialAction implements Action {
    BERSERK,
    ULTRA_BERSERK,
    GREEDY_PICK_UP,
    RAGE,
    EMP_COMBO;

    private GoAction greedyPickUpAction;
    private Bot bot;

    private static final UseAction[] BERSERK_ACTIONS = {
            UseAction.EMP_FORCE,
            UseAction.CYBERTRUCK_FORCE,
            UseAction.BOOST_FORCE,
            UseAction.OIL_FORCE,
            UseAction.LIZARD_FORCE
    };

    public boolean isFeasibleFor(Bot bot) {
        this.bot = bot;
        switch (this) {
            case BERSERK:
                return SpecialAction.berserk(bot) != null
                        && bot.player().hasAnyPowerUp() && (
                        bot.player().nearlyFinish()
                                || bot.opponent().nearlyFinish()
                );
            case ULTRA_BERSERK:
                return  SpecialAction.berserk(bot) != null
                        && bot.player().hasAnyPowerUp() && (
                        bot.player().hasPastHalfway()
                                || bot.opponent().hasPastHalfway()
                );
            case RAGE:
                return bot.player().has(PowerUp.TWEET) || (
                        bot.player().has(PowerUp.EMP)
                                && bot.getOpponentRelativePosition().
                                isWithinEMPRange()
                                && bot.isBehindOpponent()
                );
            case EMP_COMBO:
                return bot.player().has(PowerUp.EMP, 5)
                        || (
                        bot.player().is(State.USED_EMP)
                                && bot.player().has(PowerUp.EMP)
                );
            case GREEDY_PICK_UP:
            default:
                PowerUpSearchResult result = bot.searchNearestPowerUp();
                this.greedyPickUpAction = result.action;
                return result.available;
        }
    }


    public Command execute() {
        switch (this) {
            case BERSERK:
            case ULTRA_BERSERK:
                return SpecialAction.berserk(this.bot);
            case RAGE:
                if (this.bot.player().has(PowerUp.EMP)
                        && this.bot.getOpponentRelativePosition().
                        isWithinEMPRange()
                        && this.bot.isBehindOpponent()) {
                    return new EmpCommand();
                }
                if (this.bot.player().has(PowerUp.TWEET)) {
                    UseAction action = UseAction.CYBERTRUCK;
                    action.use(this.bot);
                    return action.execute();
                }
            case EMP_COMBO:
                return new EmpCommand();
            case GREEDY_PICK_UP:
            default:
                return this.greedyPickUpAction.execute();
        }
    }

    private static Command berserk(Bot bot) {
        for (UseAction action : SpecialAction.BERSERK_ACTIONS) {
            if (action.isFeasibleFor(bot)) {
                return action.execute();
            }
        }
        if (bot.player().has(PowerUp.BOOST)) {
            return UseAction.BOOST.execute();
        }
        if (bot.player().has(PowerUp.LIZARD)) {
            return UseAction.LIZARD.execute();
        }
        if (bot.player().has(PowerUp.TWEET)) {
            return UseAction.CYBERTRUCK.execute();
        }
        if (bot.isAheadOfOpponent() && bot.player().has(PowerUp.OIL)) {
            return UseAction.OIL.execute();
        }
        if (bot.isBehindOpponent() && bot.player().has(PowerUp.EMP)) {
            return UseAction.EMP.execute();
        }
        return null;
    }

}
