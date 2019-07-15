package com.pwk.java8.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWirteLock {
	
	public static void main(String[] args) {
		MyReadWirteLock lock=new MyReadWirteLock(10);
		new Thread(()-> lock.write(100)).start();
		for (int i = 0; i < 100; i++) {
			new Thread(()->{
				lock.read();
			}).start();
		}
		
	}

}

class MyReadWirteLock{
	
	private ReadWriteLock lock= new ReentrantReadWriteLock();
	
	private int num;
	
	
	public MyReadWirteLock(int num) {
		super();
		this.num = num;
	}


	public void read() {
		lock.readLock().lock();
		try {
			
			System.out.println(Thread.currentThread().getName()+":"+num);
			
		}finally {
			lock.readLock().unlock();
		}
	}
	
	public void write(int num) {
		lock.writeLock().lock();
		try {
			Thread.sleep(1);
			System.out.println("设置："+num);
			this.num=num;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}
