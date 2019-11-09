package com.imooc.o2o.util.threadTest;

class MyThread extends Thread {
    private int ticketsCount = 5;// 一共有5张火车票
    private String name;// 窗口,就是线程的名字

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (ticketsCount > 0) {
            ticketsCount--;// 如果还有票就卖掉一张
            System.out.println(name + "窗口卖了1张票,剩余票数为:" + ticketsCount);

        }
    }
}

public class TicketsThread {
    public static void main(String[] args) {
        //new了三个独立的Thread对象,他们是独立的,不是共享的,所以是卖了15张票.
        // 创建3个线程,模拟三个窗口买票
        MyThread t1 = new MyThread("窗口1");
        MyThread t2 = new MyThread("窗口2");
        MyThread t3 = new MyThread("窗口3");

        // 启动这三个线程,也就是三个窗口
        t1.start();
        t2.start();
        t3.start();

    }
}

