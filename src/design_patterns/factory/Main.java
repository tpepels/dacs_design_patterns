package design_patterns.factory;

public class Main {
    public void example() {
        // This creates two dependencies, regardless of the number of shapes
        ShapeFactory factory = new ShapeFactory(); // 1
        Shape myShape = factory.getShape("circle"); // 2

        myShape.draw();
        Shape myShape2 = factory.getShape("square");
        myShape2.draw();
    }

    public void counterExample() {
        // This creates n dependencies where n is the number of shapes needed
        Circle circle = new Circle(); // 1
        Square square = new Square(); // 2
        // .... n
        // Create new other shapes needed

        circle.draw();
        square.draw();
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.example();
        m.counterExample();
    }
}
