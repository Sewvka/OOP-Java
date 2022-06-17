package ru.nsu.fit.oop;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

class Courier implements Runnable{

    private final Warehouse warehouse;
    private final Pizzeria pizzeria;
    AtomicBoolean isFree;
    final private int carryingTime;
    final private int capacity;

    Courier(Pizzeria pizzeria, int carryingTime, int capacity) {
        this.pizzeria = pizzeria;
        this.warehouse = pizzeria.warehouse;
        this.carryingTime = carryingTime;
        this.capacity = capacity;
        this.isFree = new AtomicBoolean(true);
    }

    public boolean isFree() {
        return isFree.get();
    }

    @Override
    public void run() {
        ArrayList<Integer> deliveryOrders = new ArrayList<Integer>();
        this.isFree.set(false);
        try {
            while (warehouse.isEmpty() && pizzeria.isOpened()){

            }
            synchronized (this) {
                if (!warehouse.isEmpty()) {
                    deliveryOrders.addAll(warehouse.takeOrderList(capacity));
                }
            }
            Thread.sleep(carryingTime);
            for (int ord : deliveryOrders) {
                pizzeria.increaseDoneOrders();
                System.out.println("[order " + ord+ "] [done]");
            }
            this.isFree.set(true);
            deliveryOrders.clear();
            pizzeria.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}