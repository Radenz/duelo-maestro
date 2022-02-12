package za.co.entelect.challenge.type;

import za.co.entelect.challenge.botutils.action.GoAction;

/**
 * {@code PowerUpSearchResult} wraps the information
 * of power up availability and action to do to pick them
 * after doing a power up search.
 */
public class PowerUpSearchResult {
    public boolean available;
    public GoAction action;

    /**
     * Creates a new {@code PowerUpSearchResult} based on
     * searching result information.
     * @param available power up availability
     * @param action action to do to pick the power up,
     *               useless if {@code available} is false
     */
    public PowerUpSearchResult(boolean available, GoAction action) {
        this.available = available;
        this.action = action;
    }
}
