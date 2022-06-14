package ru.nsu.fit.oop;

import java.util.Random;

import static ru.nsu.fit.oop.State.COOKING;
import static ru.nsu.fit.oop.State.IN_STOCK;


public class Baker extends Employee implements Client<Order>, Producer<Order> {
    private static final int MAX_COOKING_TIME = 5000;
    private final int workingExperience;
    private final Random random;
    private final MyBlockingDequeue<Order> queue;
    private final MyBlockingDequeue<Order> storage;


    public Baker(int id, int workingExperience, MyBlockingDequeue<Order> queue, MyBlockingDequeue<Order> storage) {
        super(id);
        this.workingExperience = workingExperience;
        this.random = new Random();
        this.queue = queue;
        this.storage = storage;
    }

    @Override
    public Order consume() {
        try {
            Order order = queue.get();
            order.setState(COOKING);
            return order;
        } catch (InterruptedException exception) {
            System.err.println("The baker №" + getId() + " could not take the order.");
            return null;
        }
    }

    @Override
    public void produce(Order order) {
        int leadTime = random.nextInt(MAX_COOKING_TIME);
        try {
            Thread.sleep(leadTime);
            order.setState(IN_STOCK);
            storage.put(order);
        } catch (NullPointerException exception) {
            System.err.println("The baker №" + getId() + " tried to fulfill an order that does not exist.");
        } catch (InterruptedException exception) {
            System.err.println("The baker №" + getId() + "could not fulfill the order.");
        }
    }

    @Override
    public void work() {
        Order order = consume();
        if (order == null) {
            stop();
        }
        produce(order);
    }
}