package com.cj.shop.api.interf;

import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.response.PagedList;

/**
 * 用户service接口
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
    String updateAddress(UserAddress userAddress);



}
