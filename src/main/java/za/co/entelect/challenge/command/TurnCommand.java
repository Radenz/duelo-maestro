package za.co.entelect.challenge.command;

import za.co.entelect.challenge.enums.Direction;

public class TurnCommand implements Command {

    private Direction direction;

    public TurnCommand(Direction dir) {
        this.direction = dir;
    }

    @Override
    public String render() {
        return String.format("TURN_%s",
                this.direction == Direction.LEFT ?
                        "LEFT" : "RIGHT"
        );
    }
}
