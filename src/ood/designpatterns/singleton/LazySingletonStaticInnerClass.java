package ood.designpatterns.singleton;

/**
 * 外部无法访问静态内部类LazySingletonHolder, 只有调用LazySingletonStaticInnerClass.getInstance时才能得到singleton INSTANCE
 * 注意singleton INSTANCE的初始化是在调用getInstance时, 利用classloader的加载机制来实现懒加载, 并保证构建单例的线程安全
 */
public class LazySingletonStaticInnerClass {

    private LazySingletonStaticInnerClass() {}

    private static class LazySingletonHolder {
        private static final LazySingletonStaticInnerClass INSTANCE = new LazySingletonStaticInnerClass();
    }

    public static LazySingletonStaticInnerClass getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    public void print() {
        System.out.println("This is the only Lazy Singleton created by class loader.");
    }
}
