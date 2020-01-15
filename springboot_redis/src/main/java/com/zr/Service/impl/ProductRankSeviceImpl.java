package com.zr.Service.impl;

import com.zr.Service.ProductRankService;
import com.zr.entity.ProductRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ProductRankSeviceImpl implements ProductRankService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZSetOperations zSetOperations;

    @Override
    public void zSetAdd(ProductRank productRank, double count) {


        String sortName = "product:rank";
        zSetOperations.incrementScore(sortName,productRank,count);
    }

    @Override
    public List<ProductRank> getSetRank(Long begin, Long end) {

        List<ProductRank> list = new ArrayList<>();
        String sorName = "product:rank";
        Set<ProductRank> set = zSetOperations.reverseRange(sorName, begin, end);

        Iterator<ProductRank> iterator = set.iterator();
        while (iterator.hasNext()){

            ProductRank rank = iterator.next();
            list.add(rank);
        }
        return list;
    }



}
