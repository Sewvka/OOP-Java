package ru.nsu.fit.oop;

import java.io.File;
import java.net.Socket;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        Pizzeria pizzeria = new Pizzeria(new File("pizzeria.json"));
        PizzeriaManager pizzeriaManager = new PizzeriaManager(pizzeria);
        Thread mainThread = new Thread(pizzeriaManager);
        Thread clientThread = new Thread(new Client(pizzeria));
        clientThread.start();
        mainThread.start();
    }
}