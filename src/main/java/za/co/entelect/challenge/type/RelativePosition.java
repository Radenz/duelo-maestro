package za.co.entelect.challenge.type;

public enum RelativePosition {
    PAR,
    LEFT,
    RIGHT,
    FAR_LEFT,
    FAR_RIGHT,
    ACROSS;

    public boolean isWithinEMPRange() {
        return this == LEFT
                || this == PAR
                || this == RIGHT;
    }
}
