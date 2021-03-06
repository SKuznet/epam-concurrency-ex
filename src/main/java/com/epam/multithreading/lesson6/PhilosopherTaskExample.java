package com.epam.multithreading.lesson6;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PhilosopherTaskExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 1; i < 6; i++) {
            new Philosopher(semaphore, i).start();
        }
    }
}

class Philosopher extends Thread {
    // semaphore that limit count of philosopher
    private Semaphore semaphore;
    private int foodEatingCount;
    private int philosopherId;

    public Philosopher(Semaphore semaphore, int philosopherId) {
        this.semaphore = semaphore;
        this.philosopherId = philosopherId;
    }

    @Override
    public void run() {
        try {
            while (foodEatingCount < 3) {
                semaphore.acquire();
                System.out.println("Philosopher " + philosopherId + " sit at the table...");
                TimeUnit.MILLISECONDS.sleep(500);
                foodEatingCount++;

                System.out.println("Philosopher " + philosopherId + " stand up from the table...");
                semaphore.release();

                // philosopher is walking...
                TimeUnit.MILLISECONDS.sleep(500);

            }
        } catch (InterruptedException e) {
            System.err.println("Philosopher " + philosopherId + " have a problem with health...");
        }
    }
}
