package za.co.entelect.challenge.entities;

import com.google.gson.annotations.SerializedName;
import za.co.entelect.challenge.enums.Terrain;

public class Block {
    @SerializedName("position")
    public Position position;

    @SerializedName("surfaceObject")
    public Terrain terrain;

    @SerializedName("occupiedByPlayerId")
    public int occupiedByPlayerId;

    @SerializedName("isOccupiedByCyberTruck")
    public boolean occupiedByCyberTruck;

    public boolean isObstacle() {
        return this.terrain.isObstacle()
                || this.occupiedByCyberTruck;
    }

    public boolean isHardObstacle() {
        return this.terrain.isWall()
                || this.occupiedByCyberTruck;
    }
}
