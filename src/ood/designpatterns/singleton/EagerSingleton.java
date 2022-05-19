package ood.designpatterns.singleton;

public class EagerSingleton {

    // 若没有static则这个instance需要依赖于一个object而存在
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // suppresses default public constructor to private
    private EagerSingleton() {
    }

    // 必须是static
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    public void print() {
        System.out.println("This is the only Eager Singleton.");
    }
}
