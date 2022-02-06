package za.co.entelect.challenge.entities;

import com.google.gson.annotations.SerializedName;

public class Position {
    @SerializedName("y")
    public int lane;

    @SerializedName("x")
    public int block;

    public Position(int lane, int distance) {
        this.lane = lane;
        this.block = distance;
    }

    public int distance() {
        return this.block;
    }

    public int lane() {
        return this.lane;
    }
}
