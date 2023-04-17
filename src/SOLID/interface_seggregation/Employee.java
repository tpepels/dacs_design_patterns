package SOLID.interface_seggregation;

/*
 * In this example, we have an Employee interface that defines methods for
 * working, eating, and sleeping. However, the Engineer and Manager classes
 * violate the ISP because they both implement methods that may not be relevant
 * to their behavior (e.g., Manager may not need to implement work() if they are
 * only responsible for management tasks).
 */
interface BadEmployee {
    void work();

    void eat();

    void sleep();
}

class BadEngineer implements BadEmployee {
    public void work() {
        System.out.println("The engineer is working on a project.");
    }

    public void eat() {
        System.out.println("The engineer is eating lunch.");
    }

    public void sleep() {
        System.out.println("The engineer is taking a nap.");
    }
}

class BadManager implements BadEmployee {
    public void work() {
        System.out.println("The manager is managing the team.");
    }

    public void eat() {
        System.out.println("The manager is eating lunch.");
    }

    public void sleep() {
        System.out.println("The manager is taking a nap.");
    }
}

/*
 * To solve this problem, we can use interfaces
 * that are specific to each behavior, such as Worker, Eater, and Sleeper, and
 * have classes implement only the interfaces that are relevant to their
 * behavior. We can then define a new interface Employee that extends all of the
 * relevant behavior interfaces.
 * 
 * This way, classes can implement only the behavior that is relevant to them
 * and avoid implementing unnecessary methods
 */

interface Worker {
    void work();
}

interface Eater {
    void eat();
}

interface Sleeper {
    void sleep();
}

interface Employee extends Worker, Eater, Sleeper {
}

class Engineer implements Worker, Eater, Sleeper {
    public void work() {
        System.out.println("The engineer is working on a project.");
    }

    public void eat() {
        System.out.println("The engineer is eating lunch.");
    }

    public void sleep() {
        System.out.println("The engineer is taking a nap.");
    }
}

class Manager implements Eater, Sleeper {
    public void eat() {
        System.out.println("The manager is eating lunch.");
    }

    public void sleep() {
        System.out.println("The manager is taking a nap.");
    }
}

class ProjectManager implements Worker, Eater, Sleeper {
    public void work() {
        System.out.println("The project manager is managing the team's projects.");
    }

    public void eat() {
        System.out.println("The project manager is eating lunch.");
    }

    public void sleep() {
        System.out.println("The project manager is taking a nap.");
    }
}
