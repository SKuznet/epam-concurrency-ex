package com.epam.multithreading.lesson4;

import java.util.concurrent.TimeUnit;

public class WaitNotifyExample {

    static final Object key = new Object();

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.err.println(i);

//                    if (i == 3) {
//                        synchronized (key) {
//                            key.notifyAll(); // IllegalMonitorStateException
//                        }
//                    }

                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (key) {
                    key.notifyAll(); // IllegalMonitorStateException
                }
            }
        });

        System.err.println("start...");
        thread.start();
        // call number
        synchronized (key) {
            try {
                // return key
                key.wait(); // IllegalMonitorStateException
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.err.println("finish...");
    }
}
