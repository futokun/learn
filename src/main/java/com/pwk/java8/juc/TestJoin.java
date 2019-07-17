package com.pwk.java8.juc;

public class TestJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread thread=Thread.currentThread();
        for (int i = 0; i <10; i++) {
           Thread now= new Thread(new MyJon(thread));
           now.start();
           thread=now;
           Thread.sleep(10);
        }
        System.out.println("main结束");
    }


}

class MyJon implements Runnable{
    private Thread thread;
    MyJon(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程启动");

        try {
            System.out.println(thread.getName()+"线程即将插队到:"+Thread.currentThread().getName());
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"线程结束");
    }
}
