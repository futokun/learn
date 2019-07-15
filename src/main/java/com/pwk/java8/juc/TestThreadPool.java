package com.pwk.java8.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系结构：
 * 	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * 		|--**ExecutorService 子接口: 线程池的主要接口
 * 			|--ThreadPoolExecutor 线程池的实现类
 * 			|--ScheduledExecutorService 子接口：负责线程的调度
 * 				|--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * 三、工具类 : Executors 
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 */
public class TestThreadPool {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//创建线程池
//		ExecutorService pool = Executors.newFixedThreadPool(8);
//		
//		
//		for (int i = 0; i < 100; i++) {
//			pool.submit(()->{
//				int sum=0;
//				for (int j = 0; j < 1000; j++) {
//					sum+=j;
//					
//				}
//				System.out.println(Thread.currentThread().getName()+" : "+sum);
//			});
//		}
//		pool.shutdown();
		
		
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < 100; i++) {
			ScheduledFuture<Integer> future = pool.schedule(()->{
				int sum=0;
				for (int j = 0; j < 1000; j++) {
					sum+=j;
					
				}
				System.out.println(Thread.currentThread().getName()+" : "+sum);
				return sum;
			}, 1, TimeUnit.SECONDS);
//			System.out.println(future.get());//因为callable的get方法是阻塞的，所以会一秒执行一个，如果不调用get方法，所有的线程都会等一秒后同时执行
		}
		pool.shutdown();
		
		
		
	}
	
}
