package SOLID.open_closed;

/*
 * In this example, the AreaCalculator class violates the Open/Closed Principle because when we want to add a 
 * new shape, we need to modify the calculateArea method to account for the new shape.
 */
class BadShape {
    public String type;
}

class BadCircle extends BadShape {
    public double radius;

    public BadCircle(double radius) {
        this.type = "circle";
        this.radius = radius;
    }
}

class BadRectangle extends BadShape {
    public double width;
    public double height;

    public BadRectangle(double width, double height) {
        this.type = "rectangle";
        this.width = width;
        this.height = height;
    }
}

class BadAreaCalculator {
    public double calculateArea(BadShape shape) {
        if (shape.type.equals("circle")) {
            BadCircle circle = (BadCircle) shape;
            return Math.PI * circle.radius * circle.radius;
        } else if (shape.type.equals("rectangle")) {
            BadRectangle rectangle = (BadRectangle) shape;
            return rectangle.width * rectangle.height;
        }
        throw new IllegalArgumentException("Invalid shape type");
    }
}

/*
 * In this solution, we define a Shape interface with a calculateArea method.
 * Each shape class (Circle, Rectangle, etc.) implements the Shape interface and
 * provides its own implementation of the calculateArea method. The
 * 
 * AreaCalculator class no longer needs to be modified when adding new shapes,
 * as it simply calls the calculateArea method on the provided Shape instance,
 * respecting the Open/Closed Principle.
 */

interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea();
    }
}
