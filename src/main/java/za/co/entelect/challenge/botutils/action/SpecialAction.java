package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.FixCommand;
import za.co.entelect.challenge.Bot;

public enum SpecialAction implements Action {
    BERSERK,
    ULTRA_BERSERK,
    GREEDY_PICK_UP,
    RAGE,
    EMP_COMBO;

    public boolean isFeasibleFor(Bot bot) {
//        TODO: CHANGE ME !!
        return true;
    }


    public Command execute() {
        return new FixCommand();
    }

}
