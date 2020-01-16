package com.zr;

import com.zr.service.SecKillService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;


@SpringBootTest
public class SeckillTest {


    private static final int Max_THREADS = 200;

    @Autowired
    private SecKillService secKillServic;

    private static CountDownLatch count = new CountDownLatch(Max_THREADS);

    @Test
    public  void setSecKillTest1(){

        System.out.println("12132");
        String s = secKillServic.querySecKillProductInfo("123456");
        System.out.println(s);
    }

    @Test
    public void setSecKillTest2() {

       /* for (int i = 0; i < Max_THREADS; i++) {
            System.out.println(i);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                      System.out.println(Thread.currentThread().getName() + "准备");
                        count.countDown();
                        count.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    secKillServic.orderProductMockDiffUser("123456");

                }
            }).start();


            Thread.currentThread().sleep(2000);
        }*/

        CyclicBarrier barrier = new CyclicBarrier(10);

        for(int i = 0;i < 10;i++){
            Thread t=  new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "准备");
                        barrier.await();//10个线程等待在这里 才开始执行下面的

                        secKillServic.orderProductMockDiffUser("123456");

                        String s = secKillServic.querySecKillProductInfo("123456");
                        System.out.println(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        //这一段可以不要
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "起跑");
            System.out.println( barrier.getParties()+"--" +barrier.getNumberWaiting());
        }catch (Exception e) {
            e.printStackTrace();

        }

    }

}
