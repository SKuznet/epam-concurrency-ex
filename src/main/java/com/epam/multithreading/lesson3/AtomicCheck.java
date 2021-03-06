package com.epam.multithreading.lesson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicCheck implements Runnable {
    private int i = 0;

    public int getValue() {
        return i;
    }

    private synchronized void evenIncrement() {
        i++;
        i++;
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicCheck atomicCheck = new AtomicCheck();
        executorService.execute(atomicCheck);
        while (true) {
            int val = atomicCheck.getValue();

            if (val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
