package com.epam.multithreading.lesson5.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static void main(String[] args) {
        Store store = new Store();
        Manufacturer manufacturer = new Manufacturer(store);
        Consumer consumer = new Consumer(store);
        new Thread(manufacturer).start();
        new Thread(consumer).start();
    }
}

class Store {
    private ReentrantLock lock;
    private Condition condition;
    private int product;

    Store() {
        // create lock
        lock = new ReentrantLock();
        // create condition associated with lock
        condition = lock.newCondition();
    }

    public void get() {
        lock.lock();

        try {
            while (product < 1) {
                condition.await();
            }

            product--;
            System.out.println("Buyer buy 1 product");
            System.out.println("Products on storage: " + product);

            // signal to all
            condition.signalAll();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void put() {
        lock.lock();

        try {
            // while in stock 3 products waiting a place
            while (product >= 3) {
                condition.await();
            }

            product++;
            System.out.println("Manufacturer added 1 new product");
            System.out.println("Products on storage: " + product);

            // signal to all
            condition.signalAll();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}


class Manufacturer implements Runnable {
    private Store store;

    public Manufacturer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            store.put();
        }
    }
}


// client class
class Consumer implements Runnable {
    private Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            store.get();
        }
    }
}