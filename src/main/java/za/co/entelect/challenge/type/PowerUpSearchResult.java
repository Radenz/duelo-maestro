package za.co.entelect.challenge.type;

import za.co.entelect.challenge.botutils.action.GoAction;

public class PowerUpSearchResult {
    public boolean available;
    public GoAction action;

    public PowerUpSearchResult(boolean available, GoAction action) {
        this.available = available;
        this.action = action;
    }
}
