package com.zr.Service;

import com.zr.entity.ProductRank;

import java.util.List;

public interface ProductRankService {

    // 若没有该数据记录则添加该数据以及score 若有则直接给score加count
    public void zSetAdd(ProductRank productRank, double count);


    /**
     * 查看排行消息
     * @param begin
     * @param end
     * @return
     */

    public List<ProductRank> getSetRank(Long begin, Long end);
}
