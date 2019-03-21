package com.epam.multithreading.lesson1;

public class MoreBasicThreads {

    public static void main(String[] args) {
        for (int i = 0; i < 5 ; i++) {
            new Thread(new CastSpell()).start();
        }
        System.out.println("Waiting for cast spell");
    }
}
