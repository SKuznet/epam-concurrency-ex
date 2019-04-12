package com.epam.multithreading.lesson9;

public class DeadLockExample implements Runnable {

    private Consumer consumer = new Consumer();
    private Producer producer = new Producer();

    public DeadLockExample() {
        Thread.currentThread().setName("Главный поток");
        Thread t = new Thread(this, "Соперничающий поток");
        t.start();

        // получить блокировку для объекта consumer
        // в этом потоке исполнения
        consumer.buySomething(producer);

        System.out.println("Назад в главный поток");
    }

    public static void main(String[] args) {
        new DeadLockExample();
    }

    public void run() {
        // получить блокировку для объекта producer
        // в другом потоке исполнения
        producer.produceSomething(consumer);
        System.out.println("Назад в другой поток");
    }
}

class Consumer {

    synchronized void buySomething(Producer producer) {

        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод Consumer.buySomething()");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Класс Consumer прерван");
        }

        System.out.println(name + " пытается вызвать метод Producer.last()");
        producer.last();
    }

    synchronized void last() {
        System.out.println("В методе Consumer.last()");
    }
}

class Producer {

    synchronized void produceSomething(Consumer consumer) {

        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод Producer.produceSomething()");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Класс Producer прерван");
        }

        System.out.println(name + " пытается вызвать метод Consumer.last()");
        consumer.last();
    }

    synchronized void last() {
        System.out.println("В методе Producer.last()");
    }
}


