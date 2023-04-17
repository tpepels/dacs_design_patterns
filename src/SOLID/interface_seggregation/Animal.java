package SOLID.interface_seggregation;

/*
 * In this example, we have an Animal interface that defines methods for eating,
 * sleeping, swimming, and flying. However, the Dog and Bird classes violate the
 * ISP because they both implement methods that are not relevant to their
 * behavior (i.e., Dog should not have to implement fly(), and Bird should not
 * have to implement swim()).
 */

interface BadAnimal {
    void eat();

    void sleep();

    void swim();

    void fly();
}

class BadDog implements BadAnimal {
    public void eat() {
        System.out.println("The dog is eating.");
    }

    public void sleep() {
        System.out.println("The dog is sleeping.");
    }

    public void swim() {
        System.out.println("The dog is swimming.");
    }

    public void fly() {
        throw new UnsupportedOperationException("Dogs cannot fly.");
    }
}

class BadBird implements BadAnimal {
    public void eat() {
        System.out.println("The bird is eating.");
    }

    public void sleep() {
        System.out.println("The bird is sleeping.");
    }

    public void swim() {
        throw new UnsupportedOperationException("Birds cannot swim.");
    }

    public void fly() {
        System.out.println("The bird is flying.");
    }
}

/*
 * To solve this problem, we can use interfaces that are specific to each
 * behavior, such as Swimable and Flyable, and have classes implement only the
 * interfaces that are relevant to their behavior.
 */
interface Animal {
    void eat();

    void sleep();
}

interface Swimable {
    void swim();
}

interface Flyable {
    void fly();
}

class Dog implements Animal, Swimable {
    public void eat() {
        System.out.println("The dog is eating.");
    }

    public void sleep() {
        System.out.println("The dog is sleeping.");
    }

    public void swim() {
        System.out.println("The dog is swimming.");
    }
}

class Bird implements Animal, Flyable {
    public void eat() {
        System.out.println("The bird is eating.");
    }

    public void sleep() {
        System.out.println("The bird is sleeping.");
    }

    public void fly() {
        System.out.println("The bird is flying.");
    }
}
