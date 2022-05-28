package concurrent;

public class PrintInOrder {

    private static int counter = 1;
    private final static int N = 10;

    private synchronized void printEvenNumber() throws InterruptedException {
        while (counter < N) {
            while (counter % 2 == 1) {
                wait();
            }
            System.out.print(counter++ + " ");
            notifyAll();
        }
    }

    private synchronized void printOddNumber() throws InterruptedException {
        while (counter < N) {
            while (counter % 2 == 0) {
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
        PrintInOrder ins = new PrintInOrder();
        ins.printOneByOne();
    }
}
