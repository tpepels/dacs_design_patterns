package design_patterns.singleton;

public final class Singleton {
    private static Singleton INSTANCE;
    private String info = "Initial  info  class";

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }

        return INSTANCE;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return this.info;
    }

    // any other methods ...
}
