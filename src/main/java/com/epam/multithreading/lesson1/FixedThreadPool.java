package com.epam.multithreading.lesson1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String[] args) {

        // hi cost operation for creation thread ran only once
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new CastSpell());
        }
        executorService.shutdown();
    }
}
