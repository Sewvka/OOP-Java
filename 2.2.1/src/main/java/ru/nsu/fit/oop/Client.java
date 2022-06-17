package ru.nsu.fit.oop;

public class Client implements Runnable{
    Pizzeria pizzeria;


    public Client(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        while(!pizzeria.isOpened()){

        }
        while(pizzeria.isOpened() && pizzeria.canReceiveOrder()) {
            try {
                System.out.println("[order created]");
                pizzeria.newOrder();
                Thread.sleep((int) (1 + Math.random() * 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}