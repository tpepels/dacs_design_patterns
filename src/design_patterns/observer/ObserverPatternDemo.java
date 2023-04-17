package design_patterns.observer;

public class ObserverPatternDemo {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        new HexObserver(subject);

        System.out.println("First  state:  15");
        subject.setState(15);

        System.out.println("\n\nSecond  state:  10");
        subject.setState(10);
    }
}
