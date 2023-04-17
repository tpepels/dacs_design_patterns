package SOLID.liskov;

/*
 * In this example, we have a Bird class with a fly() method, and an Ostrich
 * class that extends Bird. However, the Ostrich class violates the LSP because
 * it throws an exception when the fly() method is called, rather than behaving
 * like a bird and actually flying.
 */
class BadBird {
    public void fly() {
        System.out.println("The bird is flying.");
    }
}

class BadOstrich extends BadBird {
    public void fly() {
        throw new UnsupportedOperationException("Ostriches cannot fly.");
    }
}

/*
 * To solve this, we should only include methods that are common to all birds in
 * our interface
 */
interface Bird {
    void eat();
}

class FlyingBird implements Bird {
    public void fly() {
        System.out.println("The bird is flying.");
    }

    @Override
    public void eat() {
        System.out.println("Delicious!");
    }
}

class Ostrich implements Bird {

    @Override
    public void eat() {
        System.out.println("Delicious! I'm eating from the soil.");
    }
}
