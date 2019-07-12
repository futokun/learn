package com.pwk.java8.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	public static void main(String[] args) {
		Ticket ticket=new Ticket();
		new Thread(ticket,"1号窗口").start();
		new Thread(ticket,"2号窗口").start();
		new Thread(ticket,"3号窗口").start();
		
		
	}
}

class Ticket implements Runnable{

	private int num=100;
	
	private Lock lock=new ReentrantLock();
	
	@Override
	public void run() {
		while (true) {
			lock.lock();//加锁
			try {
				if (num>0) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"完成售票，当前剩余票数"+--num);
				}else break;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lock.unlock();//释放锁
			}
			
			
		}
	}
	
}
