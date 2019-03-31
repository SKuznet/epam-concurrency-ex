package com.epam.multithreading.lesson5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyLockExample implements Runnable {
    private Resource resource;
    private Lock lock;

    public ConcurrencyLockExample(Resource resource) {
        this.resource = resource;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        System.err.println("trying to write to file");
        // trying to take lock in 5 sec
        boolean isLocked = false;

        try {
            isLocked = lock.tryLock(10, TimeUnit.MILLISECONDS);

            if (isLocked) {
                TimeUnit.SECONDS.sleep(2);
                resource.writeToFile();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (isLocked) {
                lock.unlock();
            } else {
                System.err.println("writing failed");
            }
        }

        // no need thread-safe for logging
        resource.doLogging();
    }
}
