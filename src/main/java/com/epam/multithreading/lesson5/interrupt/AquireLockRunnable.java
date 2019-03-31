package com.epam.multithreading.lesson5.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AquireLockRunnable implements Runnable {
    private int id;
    private boolean isInterrupt;
    private ReentrantLock lock;

    public AquireLockRunnable(int id, ReentrantLock lock) {
        this(id, lock, true);
    }

    public AquireLockRunnable(int id, ReentrantLock lock, boolean isInterrupt) {
        this.id = id;
        this.isInterrupt = isInterrupt;
        this.lock = lock;
    }

    @Override
    public void run() {
        print("Trying to lock...");
        try {
            if (isInterrupt) {
                lock.lockInterruptibly();
            } else {
                lock.lock();
            }
        } catch (InterruptedException e) {
            print("Acquiring lock failed due to " + e);
            return;
        }

        print("Got lock(" + id + ")");
        try {
            try {
                if (id == 1) {
                    TimeUnit.SECONDS.sleep(3);
                } else {
                    TimeUnit.MILLISECONDS.sleep(2500);
                }
            } catch (InterruptedException e) {
                print("Sleep interrupted");
            }
        } finally {
            lock.unlock();
            print("Unlocked(" + id + ")");
        }
    }

    private static void print(String p) {
        System.err.println(Thread.currentThread().getName() + ": " + p);
    }
}
