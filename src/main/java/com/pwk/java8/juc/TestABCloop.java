package com.pwk.java8.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCloop {

	public static void main(String[] args) {
		Loop loop=new Loop();
		new Thread(()->{
			for (int i = 0; i < 10; i++) {
				loop.loopA();
			}
		},"A").start();
		new Thread(()->{
			for (int i = 0; i < 10; i++) {
				loop.loopB();
			}
		},"B").start();
		new Thread(()->{
			for (int i = 0; i < 10; i++) {
				loop.loopC();
			}
		},"C").start();
	}
}

class Loop {

	private int flag=1;
	
	private Lock lock =new ReentrantLock();
	
	Condition conditionA = lock.newCondition();
	Condition conditionB = lock.newCondition();
	Condition conditionC = lock.newCondition();
	
	public void loopA() {
		lock.lock();
		try {
			if (flag!=1) {
				conditionA.await();//A等待
			}
			System.out.print(Thread.currentThread().getName());
			flag=2;
			conditionB.signal();//唤醒B
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	public void loopB() {
		lock.lock();
		try {
			if (flag!=2) {
				conditionB.await();//B等待
			}
			System.out.print(Thread.currentThread().getName());
			flag=3;
			conditionC.signal();//唤醒C
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	public void loopC() {
		lock.lock();
		try {
			if (flag!=3) {
				conditionC.await();//C等待
			}
			System.out.print(Thread.currentThread().getName());
			flag=1;
			conditionA.signal();//唤醒A
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}