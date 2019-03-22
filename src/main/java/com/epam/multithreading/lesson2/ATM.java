package com.epam.multithreading.lesson2;

public class ATM {
    static int money = 100;

     static void getMoney(int amount) { // toilet
        if (amount <= money) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            money-=amount;
            System.err.println("All OK. New amount: " + money);
        } else {
            System.err.println("Not enough money");
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.err.println("Mike");
                getMoney(50);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("Jack");
                getMoney(50);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("Travis");
                getMoney(50);
            }
        }).start();
    }
}
