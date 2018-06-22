package com.cj.shop.api.interf;

import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.entity.UserCart;
import com.cj.shop.api.response.PagedList;

import java.util.List;
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
    String addCart(UserCart userCart);

    /**
     * 修改购物车商品
     */
    String updateCart(UserCart userCart, Map<String, Object> properties);

    /**
     * 删除购物车商品
     */
    String deleteFromCart(Long cartId, Long uid);

    /**
     * 查询我的购物车
     */
    List<UserCart> getGoodsFromCart(Long uid, Integer pageNum, Integer pageSize);

    /**
     * 查询单条购物车详情
     */
    UserCart getCartGoodById(Long cartId, Long uid);

}
