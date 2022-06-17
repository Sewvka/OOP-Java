package ru.nsu.fit.oop;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class PizzeriaManager implements Runnable {
    private final Pizzeria pizzeria;

    public PizzeriaManager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        ThreadPoolExecutor serviceBake = (ThreadPoolExecutor) Executors.newFixedThreadPool(pizzeria.getBakersCount());
        ThreadPoolExecutor serviceCour = (ThreadPoolExecutor) Executors.newFixedThreadPool(pizzeria.getCouriersCount());
        Baker[] bakers = new Baker[pizzeria.getBakersCount()];
        for (int i = 0; i < bakers.length; i++) {
            bakers[i] = new Baker(pizzeria, pizzeria.bakersExp.get(i));
        }
        Courier[] couriers = new Courier[pizzeria.getCouriersCount()];
        for (int i = 0; i < couriers.length; i++) {
            couriers[i] = new Courier(pizzeria, pizzeria.couriersSpeed.get(i), pizzeria.couriersCapacity);
        }
        Warehouse warehouse = pizzeria.getWarehouse();
        OrdersQueue orderQueue = pizzeria.orders;

        while (pizzeria.isOpened()) {

            for (Baker b : bakers) {
                if (orderQueue.isEmpty()) break;
                if (b.isFree()) {
                    synchronized (this) {
                        serviceBake.submit(b);
                    }
                }
            }
            for (Courier c : couriers) {
                if (warehouse.isEmpty()) break;
                if (c.isFree()) {
                    serviceCour.submit(c);
                }
            }
        }
        serviceBake.shutdown();
        serviceCour.shutdown();
    }
}
