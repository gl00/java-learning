package basic.interfaces_inheritance.inheritance;

public class MountainBike extends Bicycle {

    // the MountainBike subclass addBox one field
    private int seatHeight;

    // the MountainBike subclass has one constructor
    public MountainBike(int startCadence, int startGear, int startSpeed, int startHeight) {
        super(startCadence, startGear, startSpeed);
        seatHeight = startHeight;
    }

    // the MountainBike addBox one method
    public void setSeatHeight(int newValue) {
        seatHeight = newValue;
    }
}
