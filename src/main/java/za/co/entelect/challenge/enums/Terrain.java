package za.co.entelect.challenge.enums;

import com.google.gson.annotations.SerializedName;

public enum Terrain {
    @SerializedName("0")
    EMPTY,
    @SerializedName("1")
    MUD,
    @SerializedName("2")
    OIL_SPILL,
    @SerializedName("3")
    OIL_POWER,
    @SerializedName("4")
    FINISH,
    @SerializedName("5")
    BOOST,
    @SerializedName("6")
    WALL,
    @SerializedName("7")
    LIZARD,
    @SerializedName("8")
    TWEET,
    @SerializedName("9")
    EMP;

    public boolean isPowerUp() {
        return this == Terrain.OIL_POWER
                || this == Terrain.LIZARD
                || this == Terrain.TWEET
                || this == Terrain.BOOST
                || this == Terrain.EMP;
    }

    public boolean isObstacle() {
        return this == Terrain.MUD
                || this == Terrain.OIL_SPILL
                || this == Terrain.WALL;
    }

    public boolean isWall() {
        return this == Terrain.WALL;
    }

    public boolean is(PowerUp powerUp) {
        switch (powerUp) {
            case EMP:
                return this == EMP;
            case TWEET:
                return this == TWEET;
            case BOOST:
                return this == BOOST;
            case OIL:
                return this == OIL_POWER;
            case LIZARD:
            default:
                return this == LIZARD;
        }
    }
}