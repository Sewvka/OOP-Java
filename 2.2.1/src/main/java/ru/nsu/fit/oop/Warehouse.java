package ru.nsu.fit.oop;

import java.util.ArrayList;
import java.util.LinkedList;


public class Warehouse {
    private final int warehouseSize;
    private final LinkedList<Integer> storage;



    public Warehouse(int warehouseSize) {
        this.warehouseSize = warehouseSize;
        storage = new LinkedList<>();
    }

    synchronized void storeOrder(int order) throws InterruptedException {
        while (storage.size() >= warehouseSize) wait();
        storage.add(order);
        System.out.println("[order " + order + "], [in warehouse]");
        notifyAll();
    }

    synchronized ArrayList<Integer> takeOrderList(int capacity) throws InterruptedException {
        while (storage.isEmpty()) wait();
        ArrayList<Integer> bulk = new ArrayList<>();
        while (!storage.isEmpty() && bulk.size() < capacity) {
            int order = storage.removeFirst();
            System.out.println("[order " + order + "], [in delivery]");
            bulk.add(order);
        }
        if (!bulk.isEmpty()) notifyAll();
        return bulk;
    }

    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}