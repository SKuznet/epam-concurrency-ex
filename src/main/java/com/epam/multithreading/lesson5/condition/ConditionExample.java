package com.epam.multithreading.lesson5.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*Применение условий в блокировках позволяет добиться контроля над управлением доступом к потокам. Условие блокировки
представлет собой объект интерфейса Condition из пакета java.util.concurrent.locks.

Применение объектов Condition во многом аналогично использованию методов wait/notify/notifyAll класса Object, 
которые были рассмотрены в одной из прошлых тем. В частности, мы можем использовать следующие методы интерфейса Condition:

    await: поток ожидает, пока не будет выполнено некоторое условие и пока другой поток не вызовет методы signal/signalAll.
        Во многом аналогичен методу wait класса Object

    signal: сигнализирует, что поток, у которого ранее был вызван метод await(), может продолжить работу. Применение
    аналогично использованию методу notify класса Object

    signalAll: сигнализирует всем потокам, у которых ранее был вызван метод await(), что они могут продолжить работу. 
        Аналогичен методу notifyAll() класса Object

Эти методы вызываются из блока кода, который попадает под действие блокировки ReentrantLock. Сначала, используя эту 
блокировку, нам надо получить объект Condition:

ReentrantLock locker = new ReentrantLock();
Condition condition = locker.newCondition();

Как правило, сначала проверяется условие доступа. Если соблюдается условие, то поток ожидает, пока условие не изменится:
1
2
	
while (условие)
    condition.await();

После выполнения всех действий другим потокам подается сигнал об изменении условия:
1
	
condition.signalAll();

Важно в конце вызвать метод signal/signalAll, чтобы избежать возможности взаимоблокировки потоков.
*/

public class ConditionExample {
    public static void main(String[] args) {
        Store store = new Store();
        Manufacturer manufacturer = new Manufacturer(store);
        Consumer consumer = new Consumer(store);
        new Thread(manufacturer).start();
        new Thread(consumer).start();
    }
}

class Store {
    private ReentrantLock lock;
    private Condition condition;
    private int product;

    Store() {
        // create lock
        lock = new ReentrantLock();
        // create condition associated with lock
        condition = lock.newCondition();
    }

    public void get() {
        lock.lock();

        try {
            while (product < 1) {
                condition.await();
            }

            product--;
            System.out.println("Buyer buy 1 product");
            System.out.println("Products on storage: " + product);

            // signal to all
            condition.signalAll();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void put() {
        lock.lock();

        try {
            // while in stock 3 products waiting a place
            while (product >= 3) {
                condition.await();
            }

            product++;
            System.out.println("Manufacturer added 1 new product");
            System.out.println("Products on storage: " + product);

            // signal to all
            condition.signalAll();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}


class Manufacturer implements Runnable {
    private Store store;

    public Manufacturer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            store.put();
        }
    }
}


// client class
class Consumer implements Runnable {
    private Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            store.get();
        }
    }
}
