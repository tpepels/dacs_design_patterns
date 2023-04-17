package design_patterns.singleton;

public class Main {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        s1.setInfo("This is the first instance!");

        Singleton s2 = Singleton.getInstance();
        s2.setInfo("This is the second instance");

        System.out.println(s1.getInfo());
    }
}
