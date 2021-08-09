package ood.designpatterns.singleton;

/**
 * Lazy version, 需要加锁, 不然有并发问题
 */
public final class LazySingleton {
    // 若没有static则这个instance需要依赖于一个object而存在
    private static volatile LazySingleton instance = null; // no final here

    private LazySingleton() {
        // default constructor is public
    }

    // 必须是static
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                // when more than two threads run into the first null check at the same time
                // to avoid instanced more than one time, it needs to be checked again.
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    public void print() {
        System.out.println("This is the only Lazy Singleton");
    }
}
