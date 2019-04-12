package com.epam.multithreading.lesson9;


/*
* Livelock"- тип взаимной блокировки, при котором несколько потоков продолжают свою работу,
 * но попадают в зацикленность при попытке получения каких-либо ресурсов. Фактической ошибки не возникает,
 * но КПД системы падает до 0. Часто возникает в результате попыток предотвращения deadlock'а.

Пример: Метод пытается выполнить какую-либо работу, используя 2 внешних объекта.
Сперва он получает блокировку по одному из объектов, а затем проверяет, свободен ли второй объект.
Если объект свободен - получает блокировку по нему и выполняет работу, если занят - освобождает первый объект
и ждёт, когда они оба освободятся. 2 потока одновременно вызывают этот метод. Поток 1 блокирует первый объект.
Поток 2 блокирует второй объект. Оба проверяют, свободен ли второй ресурс - обнаруживают, что он занят и
освобождают занятый ресурс. Оба потока обнаруживают, что оба ресурса свободны и начинают процесс блокировки сначала.
Livelock.

Способы борьбы:
1) Так же, как и в случае с deadlock'ом - блокировать объекты всегда в одинаковом порядке.
2) Метод может производить блокировку по своему внутреннему объекту и уже после этого пытаться
получить внешние ресурсы.
* */
public class LivelockExample {
    public static void main(String[] args) {
        final Diner husband = new Diner("Bob");
        final Diner wife = new Diner("Alice");

        final Spoon s = new Spoon(husband);

        new Thread(new Runnable() {
            public void run() {
                husband.eatWith(s, wife);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                wife.eatWith(s, husband);
            }
        }).start();
    }

    static class Spoon {
        private Diner owner;

        Spoon(Diner d) {
            owner = d;
        }

        public Diner getOwner() {
            return owner;
        }

        synchronized void setOwner(Diner d) {
            owner = d;
        }

        synchronized void use() {
            System.out.printf("%s has eaten!", owner.name);
        }
    }

    static class Diner {
        private String name;
        private boolean isHungry;

        public Diner(String n) {
            name = n;
            isHungry = true;
        }

        public String getName() {
            return name;
        }

        public boolean isHungry() {
            return isHungry;
        }

        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                // Don't have the spoon, so wait patiently for spouse.
                if (spoon.owner != this) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        continue;
                    }
                    continue;
                }

                // If spouse is hungry, insist upon passing the spoon.
                if (spouse.isHungry()) {
                    System.out.printf(
                            "%s: You eat first my darling %s!%n",
                            name, spouse.getName());
                    spoon.setOwner(spouse);
                    continue;
                }

                // Spouse wasn't hungry, so finally eat
                spoon.use();
                isHungry = false;
                System.out.printf(
                        "%s: I am stuffed, my darling %s!%n",
                        name, spouse.getName());
                spoon.setOwner(spouse);
            }
        }
    }
}
