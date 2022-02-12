package za.co.entelect.challenge.botutils;

import za.co.entelect.challenge.entities.Car;
import za.co.entelect.challenge.type.LocalMap;

/**
 * {@code Detector} is an abstract detector containing
 * player {@code Car} and current {@code LocalMap} information.
 */
public abstract class Detector {
    protected Car car;
    protected LocalMap map;
}
