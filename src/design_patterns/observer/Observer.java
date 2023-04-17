package design_patterns.observer;

public abstract class Observer {
    protected Subject subject;

    public abstract void update();
}
