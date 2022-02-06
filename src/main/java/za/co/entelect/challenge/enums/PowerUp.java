package za.co.entelect.challenge.enums;

import com.google.gson.annotations.SerializedName;

public enum PowerUp {
    @SerializedName("BOOST")
    BOOST,
    @SerializedName("OIL")
    OIL,
    @SerializedName("TWEET")
    TWEET,
    @SerializedName("LIZARD")
    LIZARD,
    @SerializedName("EMP")
    EMP;

    public static final int MAX_PRIORITY = 5;

    public int priority() {
        switch (this) {
            case EMP:
                return 5;
            case TWEET:
                return 4;
            case BOOST:
                return 3;
            case OIL:
                return 2;
            case LIZARD:
            default:
                return 1;
        }
    }
}
