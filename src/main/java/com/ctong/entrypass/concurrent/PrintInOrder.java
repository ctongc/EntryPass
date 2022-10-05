package com.ctong.entrypass.concurrent;

import java.util.concurrent.Semaphore;

public class PrintInOrder {

    static class PrintOneByOneSynchronizedMethod {
        private static int counter = 1;
        private final static int N = 10;

        private synchronized void printOddNumber() throws InterruptedException {
            while (counter < N) {
                while (counter % 2 == 0) {
                    wait();
                }
                System.out.print(counter++ + " ");
                notifyAll();
            }
        }

        private synchronized void printEvenNumber() throws InterruptedException {
            while (counter < N) {
                while (counter % 2 == 1) {
                    wait();
                }
                System.out.print(counter++ + " ");
                notifyAll();
            }
        }

        public void printOneByOne() {
            Thread t1 = new Thread(() -> {
                try {
                    printOddNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    printEvenNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            t1.start();
            t2.start();
        }

        public static void main(String[] args) {
            PrintOneByOneSynchronizedMethod ins = new PrintOneByOneSynchronizedMethod();
            ins.printOneByOne();
        }
    }

    static class PrintOneByOneSemaphore {

        private static int counter = 1;
        private final static int N = 10;
        final Semaphore sem = new Semaphore(1);

        private void printOddNumber() throws InterruptedException {
            while (counter <= N) {
                if (counter % 2 == 1) {
                    sem.acquire();
                    System.out.print(counter++ + " ");
                    sem.release();
                }
            }
        }

        private void printEvenNumber() throws InterruptedException {
            while (counter <= N) {
                if (counter % 2 == 0) {
                    sem.acquire();
                    System.out.print(counter++ + " ");
                    sem.release();
                }
            }
        }

        public void printOneByOne() {
            Thread t1 = new Thread(() -> {
                try {
                    printOddNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    printEvenNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            t1.start();
            t2.start();
        }

        public static void main(String[] args) {
            PrintOneByOneSemaphore ins = new PrintOneByOneSemaphore();
            ins.printOneByOne();
        }
    }
}
