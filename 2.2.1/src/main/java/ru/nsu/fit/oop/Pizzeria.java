package ru.nsu.fit.oop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Pizzeria implements Runnable {
    public int couriersCapacity;
    private int warehouseSize;
    private int ordersLimit;
    private int doneOrders;
    private int ordersReceived;
    private final AtomicBoolean opened;
    public OrdersQueue orders;
    public Warehouse warehouse;
    List<Integer> bakersExp;
    List<Integer> couriersSpeed;

    public Pizzeria(File pizzeriaSettings) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            PizzeriaParameters params = mapper.readValue(pizzeriaSettings, PizzeriaParameters.class);
            this.warehouseSize = params.getStorageLimit();
            this.ordersLimit = params.getOrdersLimit();
            this.couriersCapacity = params.getCouriersCapacity();
            this.bakersExp = params.getBakersExp();
            this.couriersSpeed = params.getCouriersSpeed();
            this.doneOrders = 0;
            this.ordersReceived = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.orders = new OrdersQueue(ordersLimit);
        this.warehouse = new Warehouse(warehouseSize);
        this.opened = new AtomicBoolean(true);
    }

    public boolean isOpened () {
        return opened.get();
    }

    public void newOrder() throws InterruptedException {
        orders.new_order();
        ordersReceived ++;
    }

    public boolean canReceiveOrder() {
        return !(ordersLimit == ordersReceived);
    }

    public void increaseDoneOrders() {
        doneOrders++;
    }

    public int getBakersCount() {
        return bakersExp.size();
    }

    public int getCouriersCount(){
        return couriersSpeed.size();
    }

    public int getWorkersCount() {
        return bakersExp.size() + couriersSpeed.size();
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void run () {
        opened.set(true);
        System.out.println("=======\nPizzeria is opened\n=======");
    }
    public void close () {
        if (ordersLimit == doneOrders && isOpened()) {
            System.out.println("=======\nPizzeria is closed\n=======");
            opened.set(false);
        }
    }


    static class PizzeriaParameters {
        private int[] bakersExp;
        private int[] couriersSpeed;
        private int couriersCapacity;
        private int storageLimit;
        private int ordersLimit;

        public int getOrdersLimit() {
            return ordersLimit;
        }

        public int getStorageLimit() {
            return storageLimit;
        }

        public List<Integer> getBakersExp() {
            return Arrays.stream(bakersExp).boxed().collect(Collectors.toList());
        }

        public List<Integer> getCouriersSpeed() {
            return Arrays.stream(couriersSpeed).boxed().collect(Collectors.toList());
        }

        public int getCouriersCapacity() {
            return couriersCapacity;
        }
    }
}
