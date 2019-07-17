package com.pwk.java8.utils;

import java.util.concurrent.TimeUnit;

public class SleepTools {

    /**
     * 线程睡眠秒
     * @param seconds 秒
     */
    public  static final void sleepSeconds(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);//等同于Thread.sleep，底层调用的就是Thread.sleep，
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程睡眠 毫秒
     * @param seconds  毫秒
     */
    public  static final void sleepMs(int seconds){
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);//等同于Thread.sleep，底层调用的就是Thread.sleep，
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
