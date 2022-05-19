package ood.designpatterns;

import ood.designpatterns.singleton.EagerSingleton;
import ood.designpatterns.singleton.LazySingleton;

/**
 * Singleton Pattern
 * 单例模式
 * 对象在内存中只有一个实例, 并且无需频繁的创建和销毁对象
 * Prefer lazy initialization with double-checked locking (volatile + synchronized)
 * initialization-on-demand holder idiom
 */
public class SingletonPatternDemo {

    public static void main(String[] args) {
        EagerSingleton eagerIns = EagerSingleton.getInstance();
        LazySingleton lazyIns = LazySingleton.getInstance();

        eagerIns.print();
        lazyIns.print();
    }
}
