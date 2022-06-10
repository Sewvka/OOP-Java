package ru.nsu.fit.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pizzeria implements Runnable {
    private boolean runPizzeria;
    private List<Baker> bakers;
    private List<Courier> couriers;
    private final Customers customers;
    private final MyBlockingDequeue<Order> queue;
    private final MyBlockingDequeue<Order> storage;

    private void setBakers(BakerJSON[] bakers) {
        Stream<BakerJSON> bakerJSONStream = Arrays.stream(bakers);
        this.bakers = bakerJSONStream
                .map(bakerJSON -> new Baker(bakerJSON.id(), bakerJSON.workingExperience(), this.queue, this.storage))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void setCouriers(CourierJSON[] couriers) {
        Stream<CourierJSON> courierJSONStream = Arrays.stream(couriers);
        this.couriers = courierJSONStream
                .map(courierJSON -> new Courier(courierJSON.id(), courierJSON.bagCapacity(), this.storage))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public Pizzeria(PizzeriaJSON settings) {
        this.runPizzeria = false;
        this.queue = new MyBlockingDequeue<>(settings.queueSize());
        this.storage = new MyBlockingDequeue<>(settings.storageSize());
        this.customers = new Customers(this.queue);
        setBakers(settings.bakers());
        setCouriers(settings.couriers());
    }


    @Override
    public void run() {
        runPizzeria = true;
        ExecutorService bakersThreadPool = Executors.newFixedThreadPool(bakers.size());
        ExecutorService couriersThreadPool = Executors.newFixedThreadPool(couriers.size());
        bakers.forEach(bakersThreadPool::execute);
        couriers.forEach(couriersThreadPool::execute);
        Thread customersThread = new Thread(customers);
        customersThread.start();
        System.out.println("The pizzeria is up and running!");
        while (runPizzeria && !bakersThreadPool.isTerminated() && !couriersThreadPool.isTerminated()) {
        }
        if (bakersThreadPool.isTerminated() || couriersThreadPool.isTerminated()) {
            System.out.println("Oops, something went wrong. The pizzeria is closed for a technical break.");
        }
        runPizzeria = false;
        bakersThreadPool.shutdownNow();
        couriersThreadPool.shutdownNow();
        customers.stop();
    }

    public void stop() {
        System.out.println("The pizzeria is closed. Come visit us tomorrow!");
        runPizzeria = false;
    }
}
