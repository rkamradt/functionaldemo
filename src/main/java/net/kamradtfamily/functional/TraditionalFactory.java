package net.kamradtfamily.functional;

public class TraditionalFactory {
    Car build(String type) {
        switch(type) {
            case "BlueFordFiesta":
            return new Ford("Blue");
            case "RedChevyMalibu":
            return new Chevy("Red");
        }
        throw new IllegalArgumentException("type " + type + " is not known");
    }

}
