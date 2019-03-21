package com.epam.multithreading.lesson1;

public class BasicThreads {
    public static void main(String[] args) {
        Thread thread = new Thread(new CastSpell());
        thread.start();
        System.out.println("Waiting for cast spell");
    }
}
