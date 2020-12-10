package net.kamradtfamily.functional;

public class Ford implements Car {
    private final String color;

    public Ford(String color) {
        this.color = color;
    }

    @Override
    public String getMake() {
        return "Ford";
    }

    @Override
    public String getModel() {
        return "Fiesta";
    }

    @Override
    public String getColor() {
        return color;
    }
    
}

