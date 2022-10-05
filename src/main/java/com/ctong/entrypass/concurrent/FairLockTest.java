package com.ctong.entrypass.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLockTest {

    private static final Lock fairLock = new ReentrantLock(true);
    private static final Lock unfairLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("====== Testing fair locks ======");
        new Thread(FairLockTest::test,"Thread A").start();
        new Thread(FairLockTest::test,"Thread B").start();
        new Thread(FairLockTest::test,"Thread C").start();
        new Thread(FairLockTest::test,"Thread D").start();
        new Thread(FairLockTest::test,"Thread E").start();

        Thread.sleep(12000);
        System.out.println("====== Testing unfair locks ======");
        new Thread(FairLockTest::testUnfairLock,"Thread A").start();
        new Thread(FairLockTest::testUnfairLock,"Thread B").start();
        new Thread(FairLockTest::testUnfairLock,"Thread C").start();
        new Thread(FairLockTest::testUnfairLock,"Thread D").start();
        new Thread(FairLockTest::testUnfairLock,"Thread E").start();
    }

    public static void test() {
        for (int i = 0; i < 2; i++) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " got the lock");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " released the lock");
                fairLock.unlock();
            }
        }
    }

    public static void testUnfairLock() {
        for (int i = 0; i < 2; i++) {
            try {
                unfairLock.lock();
                System.out.println(Thread.currentThread().getName() + " got the lock");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " released the lock");
                unfairLock.unlock();
            }
        }
    }
}
