package com.epam.multithreading.lesson8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * В примере BlockingQueueExample создается очередь drop типа ArrayBlockingQueue
 * емкостью в один элемент и с установленом флагом справедливости.
 * После этого запускаются два потока.
 * Первый поток Producer помещает в очередь сообщения из массива messages
 * с использованием метода put,
 * а второй поток Consumer считывает из очереди сообщения методом take и выводит их в консоль.
 * */
public class BlockingQueueExample {

    private final String[] messages = {
            "You preparing for assessment",
            "You passed the assessment",
            "You received next grade",
            "What next?"
    };
    private BlockingQueue<String> drop;

    public static void main(String[] args) {
        new BlockingQueueExample();
    }

    public BlockingQueueExample() {
        drop = new ArrayBlockingQueue<>(1, true);
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }

    class Producer implements Runnable {
        public void run() {
            try {
                int cnt = 0;
                for (String message : messages) {
                    drop.put(message);
                    if (++cnt < 3) {
                        Thread.sleep(2000);
                    }
                }
                drop.put("done");
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                String msg;
                while (!((msg = drop.take()).equals("done"))) {
                    System.out.println(msg);
                }
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
