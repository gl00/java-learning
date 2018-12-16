package basic.interfaces_inheritance.interfaces;

public class RectanglePlus implements Relatable {

    private int width = 0;
    private int height = 0;
    private Point origin;

    // four constructors

    public RectanglePlus() {
        origin = new Point(0, 0);
    }

    public RectanglePlus(Point p) {
        origin = p;
    }

    public RectanglePlus(int w, int h) {
        width = w;
        height = h;
    }

    public RectanglePlus(Point p, int w, int h) {
        origin = p;
        width = w;
        height = h;
    }

    // a method for moving the rectangle
    public void move(int x, int y) {
        origin.setX(x);
        origin.setY(y);
    }

    // a method for computing the area of the rectangle
    public int getArea() {
        return width * height;
    }

    // a method required to implement
    // the Relatable interface
    @Override
    public int isLargerThan(Relatable other) {
        RectanglePlus otherRect = (RectanglePlus) other;
        if (this.getArea() > otherRect.getArea()) {
            return 1;
        } else if (this.getArea() < otherRect.getArea()) {
            return -1;
        } else {
            return 0;
        }
    }

}
