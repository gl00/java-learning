package basic.interfaces_inheritance.interfaces;

public interface OperateCar {

    // constant declarations, if any

    // method signatures

    // An enum with values LEFT RIGHT

    int turn(Direction direction, double radius, double startSpeed, double endSpeed);

    int changeLanes(Direction direction, double startSpeed, double endSpeed);

    int signalTurn(Direction direction, boolean signalOn);

    int getRadarFront(double distanceToCar, double speedOfCar);

    int getRadarRear(double distanceToCar, double speedOfCar);

    // ......

    // more method signatures
}
