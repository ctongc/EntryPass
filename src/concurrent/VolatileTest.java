package concurrent;

public class VolatileTest {

    public static volatile boolean flag = false;

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            int i = 0;
            /* 如果没有volatile, Java编译器会跳过这个flag, 做看似正确的优化, 只做i++
             * Java的JDM的执行和编译器的优化, 出发点全都是程序里没有data race */
            while (!flag) {
                System.out.println("The thread is running..."); // try comment out and remove volatile
                // 实际上与这句print没有任何关系, 而是对flag的访问有data race的问题
                i++;
            }
            System.out.println("Thread runs " + i + " times.");
            System.out.println("The thread is finished.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread newThread = new Thread(new MyRunnable());
        newThread.start();

        /* Thread这个类的static method只操作于当前的这个thread instance -> this, Thread.currentThread()
         * 而无法在某个thread操纵别的thread去调Thread的static method
         * 比如这里如果是newThread.sleep(1000), 则依然是main thread本身会sleep, 而不会影响newThread */
        Thread.sleep(1000); // main thread sleep, newThread没有!
        flag = true; // flag对main函数的thread和创建的thread都可见, 产生data race
        // newThread.join(); no manually join here 所以main thread和newThread的结束上没有先后顺序
        System.out.println("Main thread is finished.");
    }
}