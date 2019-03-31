package com.epam.multithreading.lesson5;

public class SyncronizedLockExample implements Runnable{
    private Resource resource;

    public SyncronizedLockExample(Resource resource) {
        this.resource = resource;
    }


    @Override
    public void run() {
        synchronized (this) {
            resource.writeToFile();
        }
        resource.doLogging();
    }
}
