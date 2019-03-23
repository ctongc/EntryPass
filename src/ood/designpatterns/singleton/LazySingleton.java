package ood.designpatterns.singleton;

public final class LazySingleton {
    private static volatile LazySingleton instance = null; // 若没有static则这个instance需要依赖于一个object而存在
    private LazySingleton() { // default constructor is public
    }
    // 必须是static
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                // when more than two threads run into the first null check same time
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
