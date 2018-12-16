package basic.interfaces_inheritance.inheritance.polymophism;

public class Bicycle {

    private int cadence;
    private int gear;
    private int speed;

    public Bicycle(int cadence, int gear, int speed) {
        this.cadence = cadence;
        this.gear = gear;
        this.speed = speed;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public void applyBrake(int decrement) {
        this.speed -= decrement;
    }

    public void speedUp(int increment) {
        this.speed += increment;
    }

    public void printDescription() {
        System.out.println("\nBike is " + "input gear " + this.gear
                + " with a cadence of " + this.cadence +
                " and travelling at a speed of " + this.speed + ". ");
    }

}
