package practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 如果这个class就是一个Thread
 */
class MyThread extends Thread {
    @Override
    public void run() {
        // what this tread need to do
        System.out.println("Hello in MyThread");
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread();
        t.start();
        System.out.println("Hello2");
        t.join();
        System.out.println("Hello3");
    }
}

/**
 * 在Java世界中, 代表一个可以被并行或者并发执行的任务, 可extends
 */
class MyRunnable implements Runnable {
    private int a = 10;

    public MyRunnable() {
    }

    /* passing parameter to Java thread */
    public MyRunnable(final int a) {
        this.a = a;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            return;
        }
        System.out.println("Hello in MyRunnable");
        System.out.println(a);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new MyRunnable());
        t.start();
    }
}

class Counter {
    private int value;

    public Counter(final int value) {
        this.value = value;
    }

    public void increase() {
        synchronized(this) { // 哪个线程(this)执行它就对哪个线程上锁
            value++; // critical section
        }
        // ...
        System.out.println("increasing count");
    }

    public synchronized void decrease() { // 方法可整个synchronized
        value--;
        System.out.println("decreasing count");
    }

    public synchronized int getCount() {
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter c1 = new Counter(10);
        Counter c2 = new Counter(20);
        Thread t1 = new Thread(c1::increase);
        Thread t2 = new Thread(c1::decrease);
        Thread t3 = new Thread(c2::increase);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(c1.getCount());
        System.out.println(c2.getCount());
    }
}

class VolatileTest {
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
        /* 特别注意
         * Thread这个类的static method只操作于当前的这个thread instance -> this, Thread.currentThread()
         * 而无法在某个thread操纵别的thread去调Thread的static method
         * 比如这里如果是newThread.sleep(1000), 则依然是main thread本身会sleep, 而不会影响newThread */
        Thread.sleep(1000); // main thread sleep, newThread没有!
        flag = true; // flag对main函数的thread和创建的thread都可见, 产生data race
        // newThread.join(); no manually join here 所以main thread和newThread的结束上没有先后顺序
        System.out.println("Main thread is finished.");
    }
}

class MyBlockingQueue {
    private final Queue<Integer> queue;
    private final int limit;

    public MyBlockingQueue(final int limit) {
        this.queue = new LinkedList<>();
        this.limit = limit;
    }

    public synchronized void put(final Integer ele) { // synchronized(this)
        /* use while not if because after I woke up
         * queue may still be full, although it was not full when I was notified
         * so this while loop is used to guarantee that the queue is not full while Producer put */
        while (queue.size() == limit) {
            try {
                wait(); // this.wait(); release the lock()
                // woke up, need to check if the queue is full again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // queue.size() != limit

        /* notifyAll() since there might be consumer waiting
         * note this is synchronized so there is no other producer in the blocking queue
         * hence, no need to worry if notifyAll() will wake up other producers */
        if (queue.size() == 0) {
            /* why not notify() but notifyAll()
             * notify()只能notify一个, 如果10个wait(), 那剩下9个有可能会一直睡着 */
            notifyAll();
        }
        /* We are in synchronized method:) so the order of notify and offer doesn't matter */
        queue.offer(ele);
    }

    public synchronized Integer take() { // or get()
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (queue.size() == limit) {
                notifyAll();
            }
        }
        return queue.poll();
    }
}

class Producer implements Runnable {
    final MyBlockingQueue blockingQueue;

    public Producer(final MyBlockingQueue blockingQueue) {
        super();
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        blockingQueue.put(0);
    }
}

class Consumer implements Runnable {
    final MyBlockingQueue blockingQueue;

    public Consumer(final MyBlockingQueue blockingQueue) {
        super();
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println(blockingQueue.take());
    }
}

class ProducerConsumer {
    public static void main(String[] args) {
        final MyBlockingQueue blockingQueue = new MyBlockingQueue(20);
        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new Producer(blockingQueue)));
        }
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new Consumer(blockingQueue)));
        }
        threads.forEach(Thread::start);
    }
}

public class Concurrency {
    public static void main(String[] args) throws InterruptedException {
        // Data Race example
        final int[] a = {10};
        final int[] b = new int[1];
        Thread t1 = new Thread(() -> a[0] = 8);
        Thread t2 = new Thread(() -> {
            System.out.println(a[0] + 1);
            b[0] = a[0] + 1;
            System.out.println(b[0]);
        });
        t1.start();
        t2.start();
        System.out.println(b[0]); // b[0] is still 0 before join
        t2.join();
        System.out.println(b[0]); // 9 or 11 or anything else
    }
}
