package ood.designpatterns.singleton;

public class EagerSingleton {

    // 若没有static则这个instance需要依赖于一个object而存在
    // 因为是static final, 当类第一次加载到内存中的时候就初始化了, 所以创建的实例是thread-safe的
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
