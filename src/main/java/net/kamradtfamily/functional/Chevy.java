package net.kamradtfamily.functional;


public class Chevy implements Car {
    private final String color;

    public Chevy(String color) {
        this.color = color;
    }

    @Override
    public String getMake() {
        return "Chevy";
    }

    @Override
    public String getModel() {
         return "Malibu";
    }

    @Override
    public String getColor() {
        return color;
    }
        
}

