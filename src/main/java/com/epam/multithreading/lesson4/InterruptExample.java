package com.epam.multithreading.lesson4;

import java.util.concurrent.TimeUnit;

public class InterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println(Thread.currentThread().isInterrupted());
                for (int i = 0; i < 100000000; i++) {
                    System.err.println(i);
                    if(Thread.currentThread().isInterrupted()) {
                        System.err.println(Thread.currentThread().isInterrupted());
                        System.err.println("WakeUp!");
                        break;
                    }
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(200);
//                        // catch get back flag to false
//                    } catch (InterruptedException e) {
//
//                    }
                }
                System.err.println(Thread.currentThread().isInterrupted());
            }
        });

        System.err.println("start...");
        thread.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // why deprecated? resource is taken and un access
//        thread.stop();

        // tactic way to said that you should stop
        thread.interrupt();
        System.err.println("finish...");

    }
}
