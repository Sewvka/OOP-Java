package ru.nsu.fit.oop;

import java.util.ArrayDeque;
import java.util.Queue;

public class OrdersQueue {
    private final int max_orders_count;
    private final Queue<Integer> queue = new ArrayDeque<>();
    private int queuedOrder;

    public OrdersQueue(int limit) {
        this.max_orders_count = limit;
        this.queuedOrder = 0;
    }

    public synchronized void new_order() throws InterruptedException {
        while (isFull()) {
            wait();
        }
        boolean isEmpty = queue.isEmpty();
        queue.add(++queuedOrder);
        if (isEmpty)
            notifyAll();
    }

    public synchronized Integer get_order() throws InterruptedException {
        while (queue.isEmpty()) {
            if (max_orders_count == queuedOrder) {
                return -1;
            }
            wait();
        }
        boolean isFull = isFull();
        Integer item = queue.poll();
        if (isFull)
            notifyAll();
        return item;
    }

    private boolean isFull() {
        return queue.size() == max_orders_count;
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
