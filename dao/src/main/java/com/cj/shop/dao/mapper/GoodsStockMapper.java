package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsStock;
import com.cj.shop.api.param.select.StockSelect;
import com.cj.shop.api.response.PriceLimit;
import com.cj.shop.api.response.dto.GoodsStockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsStockMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByGoodsSn(@Param("goodsSn") String goodsSn);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(Long id);

    List<Long> selectByGoodsTypeIds(StockSelect request);

    GoodsStockDto selectByGoodsType(@Param("id") Long id);

    GoodsStockDto selectBySgoodId(@Param("sid") String sid);

    int deleteBySgoodId(@Param("sid") String sid);

    int updateByPrimaryKeySelective(GoodsStock record);

    /**
     * 统计某商品总的库存量
     */
    Integer getTotalStockNum(@Param("goodSn") String goodsSn);

    /**
     * type=1 增加
     * type=2 减少 相应的库存数量
     *
     * @param sGoodsSn
     * @return
     */
    int updateGoodsStock(@Param("sGoodsSn") String sGoodsSn, @Param("type") int type, @Param("nums") int num);

    /**
     * 统计某商品的价格阈值
     */
    PriceLimit getPriceLimit(@Param("goodSn") String goodSn);
}