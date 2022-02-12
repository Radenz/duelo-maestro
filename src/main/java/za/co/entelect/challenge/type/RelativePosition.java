package za.co.entelect.challenge.type;

/**
 * {@code RelativePosition} represents the position
 * of a {@code Car} relative to other {@code Car}
 * based on their lanes.
 */
public enum RelativePosition {
    PAR,
    LEFT,
    RIGHT,
    FAR_LEFT,
    FAR_RIGHT,
    ACROSS;

    /**
     * Checks if a {@code RelativePosition} is within
     * EMP range, that is not too far away.
     * @return true if the relative position is within
     *         EMP range, false otherwise
     */
    public boolean isWithinEMPRange() {
        return this == LEFT
                || this == PAR
                || this == RIGHT;
    }
}
