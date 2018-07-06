package com.cj.shop.api.interf;

import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.param.GoodsVisitRequest;
import com.cj.shop.api.param.UserCartRequest;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.GoodsVisitDto;
import com.cj.shop.api.response.dto.UserCartDto;

import java.util.Map;

/**
 * 用户业务相关接口
 */
public interface UserApi {

    /**
     * 用户添加收货地址
     * @param userAddress
     * @return
     */
    String addAddress(UserAddress userAddress);

    /**
     * 用户查询全部收货地址
     */
    PagedList<UserAddress> getAllAddress(Long uid, Integer pageNum, Integer pageSize);

    UserAddress getDetailById(Long uid, Long addr_id);
    /**
     * 用户删除收货地址
     */
    String deleteAddress(Long addr_id, Long uid);

    /**
     * 用户修改收货地址（包含设为默认）
     */
    String updateAddress(UserAddress userAddress, Map<String,Object> properties);


    /**
     *  加入购物车
     */
    String addCart(UserCartRequest request);

    /**
     * 删除购物车商品
     */
    String deleteFromCart(Long cartId, Long uid);

    /**
     * 查询我的购物车
     */
    PagedList<UserCartDto> getGoodsFromCart(Long uid, Integer pageNum, Integer pageSize);

    /**
     * 查询单条购物车详情
     */
    UserCartDto getCartGoodById(Long cartId, Long uid);


    /**
     * 添加访客记录
     */
    String insertGoodsVisit(GoodsVisitRequest request);

    /**
     * 查询商品访问记录列表
     * 时间倒序
     */
    PagedList<GoodsVisitDto> findAllVisit(Long uid, Integer pageNum, Integer pageSize);


    /**
     * 清除商品访问记录
     * type = all 清除全部
     * 否则根据visitId清除单条
     */
    String deleteVisit(String type, Long uid, Long visitId);

}
