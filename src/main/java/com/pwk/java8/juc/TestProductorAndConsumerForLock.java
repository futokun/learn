package com.pwk.java8.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerForLock {
	public static void main(String[] args) {
		Clerk clerk=new Clerk(0);
		new Thread(new Productor(clerk),"生产者A").start();
		new Thread(new Consumer(clerk),"消费者B").start();
		new Thread(new Productor(clerk),"生产者C").start();
		new Thread(new Consumer(clerk),"消费者D").start();
	}
	
}

//店员
class Clerk{
	private int num;
	
	private Lock lock=new ReentrantLock();
	
	private Condition con=lock.newCondition();
	
	
	public Clerk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Clerk(int num) {
		super();
		this.num = num;
	}

	public  void get() {
		lock.lock();
		try {
			while (num>=1) {//使用while循环防止虚假唤醒
				System.out.println("货已满！");
				try {
//				this.wait();
					con.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName()+"进货：第"+ ++num+"批货。");
			con.signalAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.unlock();
		}

	}
	
	public  void sale() {
		lock.lock();
		try {
			while (num<=0) {//使用while循环防止虚假唤醒
				System.out.println("货已出售完，请等待下次进货");
				try {
					con.await();;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName()+"售货：还剩"+ --num+"批货。");
			con.signalAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	
	}
}


class Productor implements Runnable{
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.get();
		}
	}

	private Clerk clerk;

	public Productor(Clerk clerk) {
		super();
		this.clerk = clerk;
	}
	
}

class Consumer implements Runnable{
	
	private Clerk clerk;

	public Consumer(Clerk clerk) {
		super();
		this.clerk = clerk;
	}


	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();		
		}
	}
	
}