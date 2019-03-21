package com.epam.multithreading.lesson1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
    public static void main(String[] args) {

        // run task at separate thread but with queue
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new CastSpell());
        }
        executorService.shutdown();
    }
}
