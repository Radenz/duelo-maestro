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

    public boolean isSoftObstacle() {
        return this.isObstacle() && !this.isHardObstacle();
    }

    public boolean isHardObstacle() {
        return this.terrain.isWall()
                || this.occupiedByCyberTruck;
    }

    public boolean isFinishLine() {
        return this.terrain.isFinishLine();
    }
}
