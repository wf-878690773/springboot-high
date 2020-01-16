package com.zr.Service.impl;


import com.zr.Service.ProductRankService;
import com.zr.entity.ProductRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductRankSeviceImpl implements ProductRankService {


    @Autowired
    private ZSetOperations zSetOperations;

    @Override
    public void zSetAdd(ProductRank productRank,double count) {

        String sortName = "product:rank";

        zSetOperations.incrementScore(sortName, productRank, count);

    }

    /**
     * 获取排行信息 zSetOperations.reverseRange
     * @param begin
     * @param end
     * @return
     * @throws IOException
     */
    @Override
    public List<Object> getSetRank(Long begin, Long end) {

        String sorName = "product:rank";

        Set rank = zSetOperations.reverseRange(sorName, begin, end);

        List<Object> list = new ArrayList<>(rank);

        return list;
    }



}
