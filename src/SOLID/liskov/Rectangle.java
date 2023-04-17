package SOLID.liskov;

/*
 * In this example, we have a Rectangle class and a Square class that extends
 * Rectangle. However, the Square class violates the LSP because it changes the
 * behavior of the setWidth() and setHeight() methods, making them set both the
 * width and height to the same value. This breaks the LSP because code that
 * expects a Rectangle to have separate width and height properties may not work
 * correctly with a Square.
 */
class BadRectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return width * height;
    }
}

class BadSquare extends BadRectangle {
    public void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}

/*
 * To solve this problem, we can use an abstract class or interface to define
 * the common behavior of Rectangle and Square. We can also create a separate
 * Square class that has a side property and getSide() method, and overrides the
 * getArea() method to calculate the area based on the side property. This way,
 * code that expects a Rectangle or Shape can work correctly with both Rectangle
 * and Square objects.
 */

abstract class Shape {
    public abstract int getArea();
}

class Rectangle extends Shape {
    protected int width;
    protected int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return width * height;
    }
}

class Square extends Shape {
    protected int side;

    public Square(int side) {
        this.side = side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    public int getArea() {
        return side * side;
    }
}
