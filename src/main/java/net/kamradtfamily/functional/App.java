package net.kamradtfamily.functional;

public final class App {
    public static void main(String[] args) {
        FunctionalFactory<Car> factory = new FunctionalFactory<>();
        factory.add("BlueFordFiesta", () -> new Ford("Blue"));
        factory.add("RedChevyMalibu", () -> new Chevy("Red"));

        Car car = factory.build("BlueFordFiesta");
        System.out.println("we built a " + car.makeModel());
        car = factory.build("RedChevyMalibu");
        System.out.println("we built a " + car.makeModel());

        TraditionalFactory tfactory = new TraditionalFactory();

        car = tfactory.build("BlueFordFiesta");
        System.out.println("we built a " + car.makeModel());
        car = tfactory.build("RedChevyMalibu");
        System.out.println("we built a " + car.makeModel());

        factory.add("BlueChevyMalibu", () -> new Chevy("Blue"));
        car = factory.build("BlueChevyMalibu");
        System.out.println("we built a " + car.makeModel());
        car = tfactory.build("BlueChevyMalibu");
        System.out.println("we built a " + car.makeModel());
    }
}
