package ood.designpatterns.singleton;

/**
 * Lazy version, double-checked locking (volatile + synchronized)
 */
public final class LazySingleton {

    // volatile可以保证instance变量在被其中一个线程new出来时，其他线程可以立即看到结果并正确的处理它
    private static volatile LazySingleton INSTANCE = null; // no final here

    // suppresses default public constructor to private
    private LazySingleton() {
    }

    // 必须是static
    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                // when more than two threads run into the first null check at the same time
                // to avoid instanced more than one time, it needs to be checked again.
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }

        return INSTANCE;
    }

    public void print() {
        System.out.println("This is the only Lazy Singleton.");
    }
}
