package com.epam.multithreading.lesson5;

public class Main {

    public static void main(String[] args) {
        ConcurrencyLockExample lockExample = new ConcurrencyLockExample(new Resource());
        Thread thread1 = new Thread(new SyncronizedLockExample(new Resource()));
        Thread thread2 = new Thread(lockExample);
        Thread thread3 = new Thread(lockExample);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
