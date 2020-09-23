package com.yhy.controller;

import java.util.concurrent.TimeUnit;

public class TestDeathLock {
    public static void main(String[] args) {
        Object lockObj1 = new Object();
        Object lockObj2 = new Object();
        //分别启动两个线程
        Thread thread1 = new Thread(new DeadLock1(lockObj1, lockObj2));
        thread1.start();
        Thread thread2 = new Thread(new DeadLock2(lockObj1, lockObj2));
        thread2.start();
    }
}

class DeadLock1 implements Runnable{
    private Object o1,o2;

    DeadLock1(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public void run() {
        fun();
    }

    private void fun(){
        synchronized (o1){
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
            synchronized (o2){

            }
        }
    }
}

class DeadLock2 implements Runnable{
    private Object o1,o2;

    DeadLock2(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public void run() {
        fun();
    }

    private void fun(){
        synchronized (o2){
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
            synchronized (o1){

            }
        }
    }
}
