package com.pwk.java8.juc;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoin {
    public static void main(String[] args) {
        Instant start = Instant.now();
        long sum = LongStream.range(1L, 10000000000L).parallel().sum();
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时："+ Duration.between(start,end).toMillis());//738

    }
    @Test
    public void test1(){
        Instant start = Instant.now();
        long sum=0;
        for (long i = 0; i <10000000000L; i++) {
            sum+=i;
        }
        System.out.println(sum);
        Instant end = Instant.now();

        System.out.println("耗时："+ Duration.between(start,end).toMillis());//2601
    }

    @Test
    public void test2(){
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate forkJoinCalculate=new ForkJoinCalculate(1L,10000000000L);
        Long sum = pool.invoke(forkJoinCalculate);

        System.out.println(sum);
        Instant end = Instant.now();

        System.out.println("耗时："+ Duration.between(start,end).toMillis());//995
    }


}
class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;

    private long end;

    private static long THRESHOLD=100000000L;

    ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    ForkJoinCalculate() {
    }

    @Override
    protected Long compute() {
        long lengh=end-start;
        if (lengh<=THRESHOLD){
            long sum=0;
            for (long i = start; i <=end; i++) {
                sum+=i;
            }
            return sum;
        }else {
            long middle=(end+start)/2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();
            ForkJoinCalculate right = new ForkJoinCalculate(middle, end);
            right.fork();

            return left.join()+right.join();
        }
    }
}