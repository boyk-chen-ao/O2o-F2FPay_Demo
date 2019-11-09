package com.imooc.o2o.util.threadTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

class DaemonThread implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("进入守护线程" + Thread.currentThread().getName());
        try {
            writeToFile();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("退出守护线程" + Thread.currentThread().getName());
    }

    private void writeToFile() throws IOException, InterruptedException {

        // file.separator这个代表系统目录中的间隔符，说白了就是斜线，不过有时候需要双线，有时候是单线，你用这个静态变量就解决兼容问题了。
        File fileName = new File("/Users/boykchen/Desktop" + File.separator + "daemon.txt");
        OutputStream os = new FileOutputStream(fileName, true);// true,说明每次都是追加操作,而不是覆盖操作
        int count = 0;
        while (count < 999) {
            os.write(("\r\nword" + count).getBytes());
            System.out.println("守护线程" + Thread.currentThread().getName() + "向文件中写入了word" + count++);
            Thread.sleep(1000);
        }
    }

}

public class DaemonThreadDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("进入主线程" + Thread.currentThread().getName());
        DaemonThread damonThread = new DaemonThread();
        Thread t = new Thread(damonThread);
        t.setDaemon(true);
        t.start();
        Scanner sc = new Scanner(System.in);//参数是System.in表示从键盘接收输入
        sc.next();//这个时候主线程就阻塞住了,一旦执行了输入操作,这个时候,阻塞就会解除掉,主线程就会继续往下执行,从而打印"退出运行".
        //由于主线程是唯一的用户线程,一旦主线程退出运行,守护线程就会退出运行.就会导致守护线程输入数据的不完整性
        System.out.println("退出主线程" + Thread.currentThread().getName());
    }
}
