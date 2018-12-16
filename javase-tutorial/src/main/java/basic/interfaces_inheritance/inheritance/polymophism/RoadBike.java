package basic.interfaces_inheritance.inheritance.polymophism;

public class RoadBike extends Bicycle {

    // input millimeters (mm)
    private int tireWidth;

    public RoadBike(int cadence, int gear, int speed, int tireWidth) {
        super(cadence, gear, speed);
        this.tireWidth = tireWidth;
    }

    public int getTireWidth() {
        return tireWidth;
    }

    public void setTireWidth(int tireWidth) {
        this.tireWidth = tireWidth;
    }

    @Override
    public void printDescription() {
        super.printDescription();
        System.out.println("The RoadBike has " + getTireWidth() + " MM tires.");
    }

}
