package com.pwk.java8.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int sum =0;
		
		for (int i = 0; i < 10; i++) {
			MyCallable myCallable=new MyCallable((i+1)*100);
			FutureTask<Integer> task=new FutureTask<Integer>(myCallable);
			
			new Thread(task).start();
			
			Integer result = task.get();
			
			System.out.println(result);
			sum+=result;
		}
		System.out.println("-----------");
		System.out.println("sum"+sum);
	}

}
/*
 * 实现callable接口可以获取返回值
 */
class MyCallable implements Callable<Integer>{
	
	private int max;

	
	
	public int getMax() {
		return max;
	}



	public void setMax(int max) {
		this.max = max;
	}



	public MyCallable() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MyCallable(int max) {
		super();
		this.max = max;
	}



	@Override
	public Integer call() throws Exception {
		int sum=0;
		for (int i = 0; i < max; i++) {
			sum+=i;
		}
		return sum;
	}
	
}