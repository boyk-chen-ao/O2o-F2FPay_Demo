package com.imooc.o2o.util.threadTest;

class MyTicketsThread implements Runnable {

    private int ticketsCount = 5;// 一共有5张票

    char a = '1';

    float b = 1.3f;

    byte c = 127;

    @Override
    public synchronized void run() {
        while (ticketsCount > 0) {
            ticketsCount--;// 如果还有票就卖掉一张
            System.out.println(Thread.currentThread().getName() + "窗口卖了1张票,剩余票数为:" + ticketsCount);

        }
    }
}

public class TicketsRunnable {
    public static void main(String[] args) {
        MyTicketsThread mt = new MyTicketsThread();//三个线程传递的是同一个runnable对象,用的也是runnable对象的同一个代码,相应的对应的资源(ticketsCount=5)也是共享的
        Thread t1 = new Thread(mt, "窗口1");
        Thread t2 = new Thread(mt, "窗口2");
        Thread t3 = new Thread(mt, "窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}
