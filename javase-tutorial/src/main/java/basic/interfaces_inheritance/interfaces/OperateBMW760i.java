package basic.interfaces_inheritance.interfaces;

public class OperateBMW760i implements OperateCar {

    // the OperateCar method signatures, with implementation --
    // for example:
    int signalOn(Direction direction, boolean signalOn) {
        // code to turn BMW's LEFT turn indicator lights on
        // code to turn BMW's LEFT turn indicator lights off
        // code to turn BMW's RIGHT turn indicator lights on
        // code to turn BMW's RIGHT turn indicator lights off
        return 0;
    }

    @Override
    public int turn(Direction direction, double radius, double startSpeed, double endSpeed) {
        return 0;
    }

    @Override
    public int changeLanes(Direction direction, double startSpeed, double endSpeed) {
        return 0;
    }

    @Override
    public int signalTurn(Direction direction, boolean signalOn) {
        return 0;
    }

    @Override
    public int getRadarFront(double distanceToCar, double speedOfCar) {
        return 0;
    }

    @Override
    public int getRadarRear(double distanceToCar, double speedOfCar) {
        return 0;
    }

    // other methods, as needed -- for example, helper classes not
    // visible to clients of the interface

}
