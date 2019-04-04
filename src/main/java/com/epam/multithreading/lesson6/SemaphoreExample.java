package com.epam.multithreading.lesson6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        ExampleResource resource = new ExampleResource();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new CountThread(resource, semaphore, "Resource count thread 1"));
        executorService.execute(new CountThread(resource, semaphore, "Resource count thread 2"));
        executorService.execute(new CountThread(resource, semaphore, "Resource count thread 3"));
        executorService.shutdown();
    }
}

class ExampleResource {
    AtomicInteger resourceCount = new AtomicInteger(0);
}

class CountThread implements Runnable {
    private ExampleResource resource;
    private Semaphore semaphore;
    private String name;

    public CountThread(ExampleResource resource, Semaphore semaphore, String name) {
        this.resource = resource;
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " waiting permission...");
            semaphore.acquire();
            resource.resourceCount.set(1);

            for (int i = 0; i < 10; i++) {
                System.out.println(this.name + ": " + resource.resourceCount.get());
                resource.resourceCount.getAndIncrement();
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(name + " release permission...");
        semaphore.release();
    }
}
