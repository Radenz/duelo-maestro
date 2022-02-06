package za.co.entelect.challenge.botutils.action;

import za.co.entelect.challenge.Bot;
import za.co.entelect.challenge.command.Command;
import za.co.entelect.challenge.command.FixCommand;
import za.co.entelect.challenge.entities.Car;

public enum FixAction implements Action {
    URGENT,
    SEMI_URGENT,
    NORMAL;

    public boolean isFeasibleFor(Bot bot) {
        Car player = bot.player;
        switch (this) {
            case URGENT:
                return player.damage >= 5;
            case SEMI_URGENT:
                return player.damage >= 3;
            case NORMAL:
            default:
                return true;
        }
    }

    public Command execute() {
        return new FixCommand();
    }
}
