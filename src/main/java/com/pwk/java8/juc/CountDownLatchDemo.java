package com.pwk.java8.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
	
	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		MyCountDownLatch latch=new MyCountDownLatch(countDownLatch);
		long startTime=System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			new Thread(latch).start();
		}
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		System.out.println("耗时："+(endTime-startTime));
	}

}

class MyCountDownLatch implements Runnable{
	private CountDownLatch latch;
	

	public MyCountDownLatch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyCountDownLatch(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public  void run() {
//		synchronized (latch) {
			try {
				for (int i = 0; i < 1000000; i++) {
					if (i%2==0) {
						System.out.println("i:"+i);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				latch.countDown();
			}
//		}

	}
	
}
