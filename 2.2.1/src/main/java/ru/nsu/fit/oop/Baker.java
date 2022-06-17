package ru.nsu.fit.oop;

import java.util.concurrent.atomic.AtomicBoolean;

public class Baker implements Runnable{
    final private Warehouse warehouse;
    final private OrdersQueue orders;
    final private int bakeTime;
    AtomicBoolean isFree;
    final private Pizzeria pizzeria;

    public Baker(Pizzeria pizzeria, int exp) {
        this.warehouse = pizzeria.warehouse;
        this.orders = pizzeria.orders;
        bakeTime = 15000/exp;
        this.isFree = new AtomicBoolean(true);
        this.pizzeria = pizzeria;
    }

    public boolean isFree() {
        return isFree.get();
    }

    @Override
    public void run() {
        try {
            if (pizzeria.isOpened()) {
                isFree.set(false);
                int orderNumber = orders.get_order();
                if (orderNumber != -1) {
                    System.out.println("[order " + orderNumber + "] [cooking]");
                    Thread.sleep(bakeTime);
                    warehouse.storeOrder(orderNumber);
                    isFree.set(true);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
