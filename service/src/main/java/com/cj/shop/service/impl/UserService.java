package com.cj.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.interf.UserApi;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.dao.mapper.UserAddressMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.util.ResultMsgUtil;
import com.cj.shop.service.util.ValidatorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/20
 * @since 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserService implements UserApi {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private JedisCache jedisCache;
    private static final String JEDIS_PREFIX_USER = "cj_shop:mall:user:";

    /**
     * 用户添加收货地址
     *
     * @param userAddress
     * @return
     */
    @Override
    public String addAddress(UserAddress userAddress) {
        int i = userAddressMapper.insertSelective(userAddress);
        if (i > 0) {
            //添加成功 加入缓存
            Long id = userAddress.getId();
            String key = JEDIS_PREFIX_USER + "address:" + id;
            jedisCache.setByDefaultTime(key, JSON.toJSONString(userAddress));
            jedisCache.del(JEDIS_PREFIX_USER + "address:list:" + userAddress.getUid());
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 用户查询全部收货地址
     *
     * @param uid
     */
    @Override
    public PagedList<List<UserAddress>> getAllAddress(Long uid, Integer pageNum, Integer pageSize) {
        String key = JEDIS_PREFIX_USER + "address:list:" + uid;
        PagedList pagedList = jedisCache.get(key, PagedList.class);
        if (pagedList == null) {
            long count = 0;
            List<UserAddress> list = null;
            list = ValidatorUtil.checkNotEmpty(userAddressMapper.selectAll(uid));
            if (pageNum != null && pageSize != null) {
                Page<Object> objects = PageHelper.startPage(pageNum, pageSize);
                count = objects.getTotal();
            } else {
                pageNum = 0;
                pageSize = 0;
            }
            pagedList = new PagedList(list, count, pageNum, pageSize);
        }
        return pagedList;
    }

    @Override
    public UserAddress getDetailById(Long addr_id) {
        String key = JEDIS_PREFIX_USER + "address:" + addr_id;
        UserAddress userAddress = jedisCache.get(key, UserAddress.class);
        if (userAddress == null) {
            userAddress = userAddressMapper.selectByPrimaryKey(addr_id);
            jedisCache.setByDefaultTime(key, JSON.toJSONString(userAddress));
        }
        return userAddress;
    }

    /**
     * 用户删除收货地址
     *
     * @param addr_id
     */
    @Override
    public String deleteAddress(Long addr_id, Long uid) {
        int i = userAddressMapper.deleteByPrimaryKey(addr_id);
        if (i > 0) {
            String key1 = JEDIS_PREFIX_USER + "address:" + addr_id;
            String key2 = JEDIS_PREFIX_USER + "address:list:" + uid;
            jedisCache.dels(new String[]{key1, key2});
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 用户修改收货地址（包含设为默认）
     *
     * @param userAddress
     */
    @Override
    public String updateAddress(UserAddress userAddress) {
        int i = userAddressMapper.updateByPrimaryKeySelective(userAddress);
        if (i > 0) {
            String key1 = JEDIS_PREFIX_USER + "address:" + userAddress.getId();
            String key2 = JEDIS_PREFIX_USER + "address:list:" + userAddress.getUid();
            jedisCache.dels(new String[]{key1, key2});
        }
        return ResultMsgUtil.dmlResult(i);
    }
}
