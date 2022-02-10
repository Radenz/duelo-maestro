package za.co.entelect.challenge.entities;

import com.google.gson.annotations.SerializedName;
import za.co.entelect.challenge.enums.PowerUp;
import za.co.entelect.challenge.enums.State;

public class Car {
    @SerializedName("id")
    public int id;

    @SerializedName("position")
    public Position position;

    @SerializedName("speed")
    public int speed;

    @SerializedName("state")
    public State state;

    @SerializedName("statesThatOccurredThisRound")
    public State[] states;

    @SerializedName("damage")
    public int damage;

    @SerializedName("powerups")
    public PowerUp[] powerups;

    @SerializedName("boosting")
    public Boolean boosting;

    @SerializedName("boostCounter")
    public int boostCounter;

    public Car clone() {
        Car newCar = new Car();
        newCar.id = this.id;
        newCar.position = new Position(this.position.lane(), this.position.distance());
        newCar.speed = this.speed;
        newCar.state = this.state;
        newCar.states = new State[this.states.length];
        for (int i = 0; i < this.states.length; i++) {
            newCar.states[i] = this.states[i];
        }
        newCar.damage = this.damage;
        newCar.powerups = new PowerUp[this.powerups.length];
        for (int i = 0; i < this.powerups.length; i++) {
            newCar.powerups[i] = this.powerups[i];
        }
        newCar.boosting = this.boosting;
        newCar.boostCounter = this.boostCounter;
        return newCar;
    }

    public int at() {
        return this.position.distance();
    }

    public int lane() {
        return this.position.lane();
    }

    public int getAcceleratedSpeed() {
        int accSpeed = 0;
        switch (this.speed) {
            case 0:
                accSpeed = 3;
                break;
            case 3:
                accSpeed = 5;
                break;
            case 5:
                accSpeed = 6;
                break;
            case 6:
                accSpeed = 8;
                break;
            case 8:
            case 9:
                accSpeed = 9;
        }
        return this.clampSpeed(accSpeed);
    }

    public int getDeceleratedSpeed() {
        int decSpeed = 0;
        switch (this.speed) {
            case 15:
                decSpeed = 9;
                break;
            case 9:
                decSpeed = 8;
                break;
            case 8:
                decSpeed = 6;
                break;
            case 6:
                decSpeed = 5;
                break;
            case 5:
                decSpeed = 3;
                break;
            case 3:
            case 0:
                decSpeed = 0;
        }
        return decSpeed;
    }

    private int clampSpeed(int speed) {
        return Math.min(speed, this.maxSpeed());
    }

    public boolean has(PowerUp powerUp) {
        for (PowerUp p : this.powerups) {
            if (p == powerUp) {
                return true;
            }
        }
        return false;
    }

    public boolean has(PowerUp powerUp, int count) {
        int powerUpCount = 0;
        for (PowerUp p : this.powerups) {
            if (p == powerUp && ++powerUpCount >= count) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyPowerUp() {
        return this.powerups.length > 0;
    }

    public boolean is(State state) {
        for (State s : this.states) {
            if (state == s) {
                return true;
            }
        }
        return false;
    }

    public int maxSpeed() {
        if (this.damage >= 5) {
            return 0;
        } else if (this.damage >= 4) {
            return 3;
        } else if (this.damage >= 3) {
            return 6;
        } else if (this.damage >= 2) {
            return 8;
        } else if (this.damage >= 1) {
            return 9;
        } else {
            return 15;
        }
    }

    public boolean canTurnLeft() {
        return !this.isInLeftmostLane();
    }

    public boolean canTurnRight() {
        return !this.isInRightmostLane();
    }

    public boolean canGainMoreSpeed() {
        return this.speed < this.maxSpeed();
    }

    public boolean isInLeftmostLane() {
        return this.lane() == 1;
    }

    public boolean isInRightmostLane() {
        return this.lane() == 4;
    }

    public boolean isBoosting() {
        return this.boostCounter > 0;
    }

    public boolean nearlyFinish() {
        return this.at() >= 1200;
    }

    public boolean hasPastHalfway() {
        return this.at() >= 750;
    }

    public int decelerate() {
        this.speed = getDeceleratedSpeed();
        return this.speed;
    }

    public boolean isHitByEMP() {
        return this.is(State.HIT_EMP);
    }

}
