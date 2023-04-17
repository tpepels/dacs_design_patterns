package SOLID.open_closed;

/*
 * In this example, the Shape class has a draw() method that checks the type of
 * the shape and draws it accordingly. This violates the OCP because if we add a
 * new shape, we would have to modify the Shape class to add the new shape to
 * the draw() method.
 */
class BadShape {
    private String type;

    public BadShape(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void draw() {
        if (type.equals("circle")) {
            System.out.println("Drawing a circle.");
        } else if (type.equals("square")) {
            System.out.println("Drawing a square.");
        }
    }
}

/*
 * To solve this problem, we can use the interface segregation principle to
 * create an interface for Shape and create separate classes that implement the
 * interface. This way, we can easily add new shapes without modifying the
 * existing code.
 */

interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing a circle.");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing a square.");
    }
}
