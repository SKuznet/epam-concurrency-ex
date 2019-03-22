package com.epam.multithreading.lesson2;

import java.util.concurrent.TimeUnit;

public class SimpleDaemons implements Runnable {
    // Daemon - это поток, предоставляющий некоторый сервис, работая в фоновом режиме во время
    // выполнения программы, но при этом не является ее неотъемлемой частью

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            System.err.println("sleep() interrupted");
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }

        System.out.println("all daemons is ran");
        try {
            TimeUnit.MILLISECONDS.sleep(175);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
