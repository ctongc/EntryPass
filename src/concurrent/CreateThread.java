package concurrent;

import java.util.UUID;

/**
 * 如果这个class就是一个Thread
 * Thread类其实是实现了Runnable接口的一个实例, 继承Thread类后需要重写run方法并通过start方法启动线程
 * 继承Thread类耦合性太强了, 因为java只能单继承, 不利于扩展
 */
class MyThread extends Thread {
    UUID uuid;

    public MyThread() {
    }

    public MyThread(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void run() {
        // what this thread needs to do
        System.out.println("Hello in MyThread.");
        System.out.println(uuid);
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread();
        t.start();
        System.out.println("Hello2");
        t.join();
        System.out.println("Hello3");
        MyThread t2 = new MyThread(UUID.randomUUID());
        t2.start();
        System.out.println("Hello4");
        t2.join();
        System.out.println("Hello5");
    }
}

/**
 * 在Java世界中, 代表一个可以被并行或者并发执行的任务, 可extends
 * 通过实现Runnable接口 → 重写run(), 把Runnable实例传给Thread对象
 * 通过Thread的start()调用Thread的run()再调用Runnable实例的run()启动线程
 */
class MyRunnable implements Runnable {
    private int a = 10;

    public MyRunnable() {
    }

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
        System.out.println("Hello in MyRunnable.");
        System.out.println(a);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new MyRunnable());
        t.start(); // concurrency: no guarantee that t will print before t2
        Thread t2 = new Thread(new MyRunnable(2));
        t2.start();
    }
}

class MyThreadWithRunnable extends Thread implements Runnable {
    // Overriding the run() in Runnable, not in Thread
    @Override
    public void run() {
        System.out.println("Hello in MyRunnable.");
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadWithRunnable t = new MyThreadWithRunnable();
        t.start();
        MyThreadWithRunnable t2 = new MyThreadWithRunnable();
        t2.start();
    }
}

public class CreateThread {
}
