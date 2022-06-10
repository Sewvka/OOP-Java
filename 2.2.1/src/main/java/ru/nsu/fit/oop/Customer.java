package ru.nsu.fit.oop;

import java.util.Random;


public class Customer implements Producer<Order> {
    private static final long MAX_ORDERING_TIME = 100;
    private final Random random;
    private final MyBlockingDequeue<Order> queue;

    public Customer(MyBlockingDequeue<Order> queue) {
        this.random = new Random();
        this.queue = queue;
    }

    @Override
    public void produce(Order order) {
        long orderingTime = MAX_ORDERING_TIME+1;
        while (orderingTime > MAX_ORDERING_TIME) {
            orderingTime = random.nextLong();
        }
        try {
            Thread.sleep(orderingTime);
            order.setState(State.IN_QUEUE);
            queue.put(order);
        } catch (NullPointerException exception) {
            System.err.println("A non-existent order was received from a customer.");
        } catch (InterruptedException exception) {
            System.err.println("The customer was unable to place an order №" + order.getId() + ".");
        }
    }
}
