package com.zr.controller;

import com.zr.service.ProductRankService;
import com.zr.entity.ProductRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductContrroller {

    @Autowired
    private ProductRankService productRankService;


    @GetMapping("product")


    @PostMapping("/add")
    public String addProduct(@RequestBody ProductRank productRank)  {

        int count = 0;
        count++;
        productRankService.zSetAdd(productRank,count);

        return "添加成功";

    }

    @PostMapping("/add1")
    public String addProduct1()  {

        return "添加成功";

    }


    @GetMapping("setRank")
    public List<Object> setRank(@RequestParam(value = "begin",defaultValue = "0") Long begin,
                                @RequestParam(value = "end",defaultValue = "-1") Long end) throws IOException {

        System.out.println(begin.byteValue()+end.byteValue());

        List<Object> rank = productRankService.getSetRank(begin, end);


        return rank;
    }

}
