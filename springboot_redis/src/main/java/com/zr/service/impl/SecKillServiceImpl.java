package com.zr.service.impl;


import com.zr.service.RedisLock;
import com.zr.service.SecKillService;
import com.zr.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-08-06 23:18
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10000; //超时时间 10s

    @Autowired
    private RedisLock redisLock;

    /**
     * 国庆活动，皮蛋粥特价，限量100000份
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;

    /**
     * 随着类的加载而执行，而且只执行一次
     */
    static
    {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();

        products.put("123456", 10);
        stock.put("123456", 10);
    }


    private String queryMap(String productId)
    {
        return "国庆活动，皮蛋粥特价，限量份"
                + products.get(productId)
                +" 还剩：" + stock.get(productId)+" 份"
                +" 该商品成功下单用户数目："
                +  orders.size() +" 人" ;
    }

    @Override
    public String querySecKillProductInfo(String productId)
    {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId)
    {
        //加锁
        long time=System.currentTimeMillis()+TIMEOUT;

        if(!redisLock.lock(productId,String.valueOf(time))){
            System.out.println("哎呀，人太多了，换个姿势再来！");
        }
        System.out.println("获取锁");

        //1.查询该商品库存，为0则活动结束。
        int stockNum = stock.get(productId);
        if(stockNum == 0) {
            System.out.println("活动结束");
        }else {
            //2.下单(模拟不同用户openid不同)
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3.减库存
            stockNum =stockNum-1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }
        System.out.println("下单成功");

        //解锁
        redisLock.unlock(productId,String.valueOf(time));

    }
}
