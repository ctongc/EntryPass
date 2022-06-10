package practice;

import java.util.*;



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
