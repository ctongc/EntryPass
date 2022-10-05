package com.ctong.entrypass.concurrent;

import java.util.concurrent.Exchanger;

public class ThreadDataExchange {
    /**
     * 两个线程数据交换
     */
    public static void test() {
        Exchanger<Object> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                Object data = ": Data in thread 0.";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Object data = ": Data in thread 1.";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 10个线程两两数据交换
     */
    public static void test2() {
        Exchanger<Object> exchanger = new Exchanger<>();

        for (int i = 0; i < 10; i++) {
            int threadNumber = i;
            new Thread(() -> {
                try {
                    Object data = ": Data in thread " + threadNumber;
                    data = exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName() + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread-test2-" + i).start();
        }
    }

    public static void main(String[] args) {
        test();
        test2();
    }
}
