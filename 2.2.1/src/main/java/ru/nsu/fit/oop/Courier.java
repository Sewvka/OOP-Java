package ru.nsu.fit.oop;

import java.util.List;
import java.util.Random;

import static ru.nsu.fit.oop.State.*;


public class Courier extends Employee implements Client<List<Order>> {
    private static final int MAX_DELIVERY_TIME = 1000;
    private final int bagCapacity;
    private List<Order> orders;
    private final MyBlockingDequeue<Order> storage;
    private final Random random;


    public Courier(int id, int bagCapacity, MyBlockingDequeue<Order> storage) {
        super(id);
        this.bagCapacity = bagCapacity;
        this.storage = storage;
        this.random = new Random();
    }

    private void setOrdersState(State state) {
        for (Order order : orders) {
            order.setState(state);
        }
    }

    @Override
    public List<Order> consume() {
        int deliveryTime = random.nextInt(MAX_DELIVERY_TIME);
        try {
            orders = storage.get(bagCapacity);
            setOrdersState(DELIVERING);
            Thread.sleep(deliveryTime);
            setOrdersState(DELIVERED);
            return orders;
        } catch (InterruptedException exception) {
            System.err.println("The courier №" + getId() + " could not deliver the order.");
            return null;
        }
    }

    @Override
    void work() {
        List<Order> orders = consume();
        if (orders == null) {
            stop();
        }
    }
}
