package com.zr;

import com.zr.service.ProductRankService;
import com.zr.entity.ProductRank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class ProductRankTest {


    @Autowired
    private ProductRankService productRankService;


    @Test
    public void addProduct1() {

        ProductRank rank1 = new ProductRank("1","aaa", "111");;

        int count = 0;
        count++;

        productRankService.zSetAdd(rank1,count);
    }
    @Test
    public void addProduct2() {

        ProductRank rank1 = new ProductRank("2","bbb","222");

        int count = 0;
        count++;

        productRankService.zSetAdd(rank1,count);

    }
    @Test
    public void addProduct3() {

        ProductRank rank3 = new ProductRank("3","ccc","333");

        int count = 0;
        count++;

        productRankService.zSetAdd(rank3, count);
    }
    @Test
    public void getRank() throws IOException {
        List<Object> setRank = productRankService.getSetRank(0l, -1l);

        for (Object rank : setRank) {
            System.out.println(rank);
        }

    }

}
