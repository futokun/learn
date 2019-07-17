package com.pwk.java8.juc;

import com.pwk.java8.utils.SleepTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 * 1.
 * public CyclicBarrier(int parties)
 * public CyclicBarrier(int parties, Runnable barrierAction)
 *
 * 解析：
 *
 * parties 是参与线程的个数
 * 第二个构造方法有一个 Runnable 参数，这个参数的意思是最后一个到达线程要做的任务
 *
 * 2.
 * public int await() throws InterruptedException, BrokenBarrierException
 * public int await(long timeout, TimeUnit unit) throws InterruptedException, BrokenBarrierException, TimeoutException
 *
 * 解析：
 *
 * 线程调用 await() 表示自己已经到达栅栏
 * BrokenBarrierException 表示栅栏已经被破坏，破坏的原因可能是其中一个线程 await() 时被中断或者超时

 */
public class TestCyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5,()->{
            System.out.println(Thread.currentThread().getName()+"最后的任务");//最后到达的线程执行最终的任务
        });
        for (int i = 0; i < 5; i++) {
            new Thread(new TaskThread(cyclicBarrier)).start();
        }
    }

    private static class TaskThread implements Runnable{
        CyclicBarrier cyclicBarrier;

        public TaskThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+":到达栅栏A");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+":冲破栅栏A");

                SleepTools.sleepSeconds(2);
                System.out.println(Thread.currentThread().getName()+":到达栅栏B");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+":冲破栅栏B");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
            }

        }
    }
}
