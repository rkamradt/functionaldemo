package net.kamradtfamily.functional;

public interface Car {
    String getMake();
    String getModel();
    String getColor();

    default String makeModel() {
        return getColor() + " " + getMake() + " " + getModel();
    }
 }
