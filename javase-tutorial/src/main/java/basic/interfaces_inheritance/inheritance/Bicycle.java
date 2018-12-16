package basic.interfaces_inheritance.inheritance;

public class Bicycle {

    // the Bicycle class has three fields
    private int cadence;
    private int gear;
    private int speed;

    // the Bicycle class has one constructor
    public Bicycle(int startCadence, int startGear, int startSpeed) {
        cadence = startCadence;
        gear = startGear;
        speed = startSpeed;
    }

    // the Bicycle class has four methods
    public void setCadence(int newValue) {
        cadence = newValue;
    }

    public void setGear(int newValue) {
        gear = newValue;
    }

    public void applyBrake(int decrement) {
        speed -= decrement;
    }

    public void speedUp(int increment) {
        speed += increment;
    }

}
