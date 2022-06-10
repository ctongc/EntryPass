package concurrent;

public class DataRaceTest {

    static class Counter {
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

    public static void main(String[] args) throws InterruptedException {
        // Data Race example
        final int[] a = {10};
        final int[] b = new int[1];
        Thread t1 = new Thread(() -> a[0] = 8);
        Thread t2 = new Thread(() -> {
            System.out.println("a[0] + 1: " + (a[0] + 1));
            b[0] = a[0] + 1;
            System.out.println("b[0]: " + b[0]);
        });

        t1.start();
        t2.start();

        System.out.println("b[0] before t2.join(): " + b[0]); // b[0] is still 0 before join
        t2.join();
        System.out.println("b[0] after t2.join(): " + b[0]); // 9 or 11 or anything else
    }
}
