package com.ctong.entrypass.ood.designpatterns.singleton;

/**
 * Lazy version, double-checked locking (volatile + synchronized)
 */
public final class LazySingleton {

    // volatile 保证可见性、禁止指令重排
    // 保证instance变量在被其中一个线程new出来时，其他线程可以立即看到结果并正确的处理它
    private static volatile LazySingleton INSTANCE = null; // no final here

    // suppresses default public constructor to private
    private LazySingleton() {
    }

    // 必须是static, 没有synchronized -> 如果用synchronized不用双检测, 则每次获取instance都要加锁
    public static LazySingleton getInstance() {
        // 如果已经创建了singleton对象, 就不用进入同步代码块去竞争锁, 提升效率
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
