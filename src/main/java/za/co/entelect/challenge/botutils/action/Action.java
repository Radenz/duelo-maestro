package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;

public interface Action {
    boolean isFeasibleFor(Bot bot);
    Command execute();
}
