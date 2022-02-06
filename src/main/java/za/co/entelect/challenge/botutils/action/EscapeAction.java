package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.FixCommand;

public enum EscapeAction implements Action {
    EMP,
    CYBERTRUCK;

    public boolean isFeasibleFor(Bot bot) {
//        TODO: CHANGE ME !!
        return true;
    }


    public Command execute() {
        return new FixCommand();
    }
}
