package ood.designpatterns.singleton;

public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton(){}

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    public void print() {
        System.out.println("This is the only Eager Singleton");
    }
}
