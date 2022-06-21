package ru.nsu.fit.oop;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PizzariaWarehouseTest {
    @Test
    public void WareHouseEmptyOnClose() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(new File("pizzeria.json"));
        PizzeriaManager pizzeriaManager = new PizzeriaManager(pizzeria);
        Thread mainThread = new Thread(pizzeriaManager);
        Thread clientThread = new Thread(new Client(pizzeria));
        clientThread.start();
        mainThread.start();
        Thread.sleep(30000);
        assertTrue(pizzeria.warehouse.isEmpty());

    }

    @Test
    public void DoneReceivedEquals() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(new File("pizzeria.json"));
        PizzeriaManager pizzeriaManager = new PizzeriaManager(pizzeria);
        Thread mainThread = new Thread(pizzeriaManager);
        Thread clientThread = new Thread(new Client(pizzeria));
        clientThread.start();
        mainThread.start();
        Thread.sleep(30000);
        assertEquals(pizzeria.getOrdersReceived(), pizzeria.getDoneOrders());
    }
}
