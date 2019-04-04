package com.epam.multithreading.lesson6;

/*
* ероятно, наиболее интересным с точки зрения синхронизации является класс Exchanger, предназначенный для
* упрощения процесса обмена данными между двумя потоками исполнения.

Принцип действия класса Exchanger очень прост: он ожидает до тех пор, пока два отдельных потока
исполнения не вызовут его метод exchange(). Как только это произойдет, он произведет обмен данны­ми,
предоставляемыми обоими потоками. Такой механизм обмена данными не только изящен, но и прост в применении.

Нетрудно представить, как воспользо­ваться классом Exchanger. Например, один поток исполнения подготавливает
буфер для приема данных через сетевое соединение, а другой - заполняет этот бу­фер данными, поступающими
через сетевое соединение. Оба потока исполнения действуют совместно, поэтому всякий раз, когда требуется
новая буферизация, осуществляется обмен данными.

Класс Exchanger является обобщенным и объявляется приведенным ниже об­разом, где параметр V определяет тип
обмениваемых данных.

В классе Exchanger определяется единственный метод exchange(), имеющий следующие общие формы:
V exchange(V буфер) throws InterruptedException
V exchange(V буфер, long ожидание, TimeUnit единица_времени)
           throws InterruptedException, TimeoutException
1
2
3

V exchange(V буфер) throws InterruptedException
V exchange(V буфер, long ожидание, TimeUnit единица_времени)
           throws InterruptedException, TimeoutException

где параметр буфер обозначает ссылку на обмениваемые данные. Возвращаются данные, полученные из другого потока
исполнения.

Вторая форма метода exchange() позволяет определить время ожидания. Главная особенность мето­да exchange()
состоит в том, что он не завершится успешно до тех пор, пока не будет вызван для одного и того же
объекта типа Exchanger из двух отдель­ных потоков исполнения.
Подобным образом метод exchange() синхронизи­рует обмен данными.

В приведенном ниже примере программы демонстрируется применения клас­са Exchanger. В этой программе
создаются два потока исполнения.

В одном пото­ке исполнения создается пустой буфер, принимающий данные из другого потока исполнения.
Таким образом, первый поток исполнения обменивает пустую сим­вольную строку на полную.
*/


import java.util.concurrent.Exchanger;

public class ExchangerExample {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new UseString(exchanger);
        new MakeString(exchanger);
    }
}

// generate symbol string
class MakeString implements Runnable {
    private Exchanger<String> exchanger;
    private String string;

    public MakeString(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        string = "";
        new Thread(this).start();
    }

    @Override
    public void run() {
        char ch = 'A';
        // fill buffer
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                string+= (char) ch++;
            }
            try {
                string = exchanger.exchange(string);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

class UseString implements Runnable {
    private Exchanger<String> exchanger;
    private String string;

    public UseString(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            // exchange empty buffer for filled

            try {
                string = exchanger.exchange(new String());

                System.out.println("Got: " + string);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

/*
* В методе main() данной программы сначала создается объект класса Exchanger. Этот объект служит для синхронизации
* обмена символьными строка­ми между классами MakeString и UseString. Класс MakeString заполняет символьную строку
* данными, а класс UseString обменивает пустую символьную стро­ку на полную, отображая затем ее содержимое.

Обмен пустой символьной строки на полную в буфере синхронизируется методом exchange(), который вызывается
из метода run() в классах MakeString и UseString.
* */
