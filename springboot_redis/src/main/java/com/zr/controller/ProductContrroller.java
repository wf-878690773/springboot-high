package com.zr.controller;

import com.zr.Service.ProductRankService;
import com.zr.entity.ProductRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductContrroller {

    @Autowired
    private ProductRankService productRankService;


    @GetMapping("product")


    @PostMapping("add")
    public String addProduct(ProductRank productRank,Double count){

        count++;

        ProductRank rank2 = new ProductRank("aaa","111");

     /*   ProductRank rank3 = new ProductRank("bbb","222");
        ProductRank rank4 = new ProductRank("bbb","222");
        ProductRank rank5 = new ProductRank("ccc","333");
        ProductRank rank6 = new ProductRank("ccc","333");
        ProductRank rank7 = new ProductRank("ccc","333");*/

        productRankService.zSetAdd(productRank,count);

        return "添加成功";

    }

    @GetMapping("setRank")
    public List<ProductRank> setRank(@RequestParam(value = "begin",defaultValue = "0") Long begin,
                                     @RequestParam(value = "end",defaultValue = "-1") Long end){

        System.out.println(begin.byteValue()+end.byteValue());

        List<ProductRank> rank = productRankService.getSetRank(begin, end);


        return rank;
    }

}
