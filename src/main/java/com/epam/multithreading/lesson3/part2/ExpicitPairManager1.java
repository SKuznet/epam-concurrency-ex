package com.epam.multithreading.lesson3.part2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpicitPairManager1 extends PairManager {
    private Lock lock = new ReentrantLock();

    @Override
    public synchronized void increment() {
        lock.lock();
        try {
            pair.incrementX();
            pair.incrementY();
            store(getPair());
        } finally {
            lock.unlock();
        }
    }
}

class ExpicitPairManager2 extends PairManager {
    private Lock lock = new ReentrantLock();
    public void increment() {
        Pair temp;
        lock.lock();
        try {
            pair.incrementX();
            pair.incrementY();
            temp = getPair();
        } finally {
            lock.unlock();
        }
        store(temp);
    }
}

class ExplicitCriticalSection {
    // check 2 way

    static void checkApproaches(PairManager pman1, PairManager pman2) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        PairManipulator pairManipulator1 = new PairManipulator(pman1);
        PairManipulator pairManipulator2 = new PairManipulator(pman2);
        PairChecker pairChecker1 = new PairChecker(pman1);
        PairChecker pairChecker2 = new PairChecker(pman2);
        executorService.execute(pairManipulator1);
        executorService.execute(pairManipulator2);
        executorService.execute(pairChecker1);
        executorService.execute(pairChecker2);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted");
        }

        System.out.println("Pm1 " + pairManipulator1 + "\npm2 " + pairManipulator2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager pairManager1 = new ExpicitPairManager1();
        PairManager pairManager2 = new ExpicitPairManager2();

        checkApproaches(pairManager1, pairManager2);
    }
}


