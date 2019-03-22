package com.epam.multithreading.lesson2;

import java.io.IOException;

public class MainVolatile {
    private static volatile boolean flag = true;

    public static void main(String[] args) {  // курица
        new ThreadFlaggSetter().start(); // яйца
        new ThreadFlaggReader().start();
    }

    public static class ThreadFlaggReader extends Thread {
        @Override
        public void run() {
            System.err.println("waiting...");
            while (flag) {

            }

            System.err.println("Go go go");
        }
    }

    public static class ThreadFlaggSetter extends Thread {
        @Override
        public void run() {
            try {
                int k = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = false;

            System.err.println("Flag now is down");
        }
    }
}
