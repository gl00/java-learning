package basic.annotation.exercise;

public interface House {
    @Deprecated
    void open();
    void openFrontDoor();
    void openBackDoor();
}