package com.cj.shop.service.impl;

import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.entity.UserCart;
import com.cj.shop.api.interf.UserApi;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.dao.mapper.UserAddressMapper;
import com.cj.shop.dao.mapper.UserCartMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import com.cj.shop.service.util.ValidatorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private UserCartMapper userCartMapper;

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
            String key = JEDIS_PREFIX_USER + "address:detail:" + id;
            jedisCache.setByDefaultTime(key, userAddress);
            jedisCache.hset(JEDIS_PREFIX_USER + "address:list:" + userAddress.getUid() + ":", id.toString(), userAddress);
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 用户查询全部收货地址
     *
     * @param uid
     */
    @Override
    public PagedList<UserAddress> getAllAddress(Long uid, Integer pageNum, Integer pageSize) {
        String key = JEDIS_PREFIX_USER + "address:list:" + uid + ":";
        long count = 0;
        Page<Object> objects = null;
        List<UserAddress> resultList = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        //查询出ID列表
        List<Long> addrIds = ValidatorUtil.checkNotEmpty(userAddressMapper.selectAllIds(uid));
        if (objects != null) count = objects.getTotal();
        //根据ID 去Redis中查询对象
        if (!addrIds.isEmpty()) {
            for (Long id : addrIds) {
                UserAddress userAddress = jedisCache.hget(key, id.toString(), UserAddress.class);
                if (userAddress == null) {
                    UserAddress detailById = getDetailById(uid, id);
                    jedisCache.hset(key, id.toString(), detailById);
                    resultList.add(detailById);
                } else {
                    resultList.add(userAddress);
                }
            }
        }
        PagedList<UserAddress> pagedList = new PagedList(resultList, count, pageNum, pageSize);
        return pagedList;
    }

    @Override
    public UserAddress getDetailById(Long uid, Long addr_id) {
        String key = JEDIS_PREFIX_USER + "address:detail:" + addr_id;
        UserAddress userAddress = jedisCache.get(key, UserAddress.class);
        if (userAddress == null) {
            userAddress = userAddressMapper.selectByPrimaryKey(uid, addr_id);
            jedisCache.setByDefaultTime(key, userAddress);
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
        UserAddress detailById = getDetailById(uid, addr_id);
        if (detailById == null) {
            return ResultMsg.ADDRESS_NOT_EXISTS;
        }

        int i = userAddressMapper.deleteByPrimaryKey(uid, addr_id);
        if (i > 0) {
            String key1 = JEDIS_PREFIX_USER + "address:detail:" + addr_id;
            String key2 = JEDIS_PREFIX_USER + "address:list:" + uid;
            jedisCache.del(key1);
            jedisCache.hdel(key2 + ":", addr_id.toString());
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 用户修改收货地址（包含设为默认）
     *
     * @param userAddress
     */
    @Override
    public String updateAddress(UserAddress userAddress, Map<String, Object> properties) {
        UserAddress detailById = getDetailById(userAddress.getUid(), userAddress.getId());
        if (detailById == null) {
            return ResultMsg.ADDRESS_NOT_EXISTS;
        }
        //
        if (1 == userAddress.getDefaultFlag()) {
            List<UserAddress> list = getAllAddress(userAddress.getUid(), null, null).getList();
            if (!list.isEmpty()) {
                for (UserAddress address : list) {
                    if (1 == address.getDefaultFlag()) {
                        return ResultMsg.DEFAULT_ADDR_ALREADY_EXIST;
                    }
                }
            }
        }
        userAddress.setProperties(PropertiesUtil.changeProperties(detailById.getProperties(), properties));
        int i = userAddressMapper.updateByPrimaryKeySelective(userAddress);
        if (i > 0) {
            String key1 = JEDIS_PREFIX_USER + "address:detail:" + userAddress.getId();
            String key2 = JEDIS_PREFIX_USER + "address:list:" + userAddress.getUid();
            jedisCache.del(key1);
            jedisCache.hdel(key2 + ":", userAddress.getId().toString());
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 加入购物车 执行逻辑：
     * 1、判断商品是否存在
     * 2、判断商品是否存在 以及规格是否相同 相同则数量+1 不同则加入一条记录
     * 3、保存加入时商品价格
     *
     * @param userCart
     */
    @Override
    public String addCart(UserCart userCart) {
        //json-property default {}
        if (StringUtils.isEmpty(userCart.getProperties())) {
            userCart.setProperties("{}");
        }
        int i = userCartMapper.insertSelective(userCart);
        if (i > 0) {
            //添加成功 加入缓存
            Long id = userCart.getId();
            String key = JEDIS_PREFIX_USER + "cart:detail:" + id;
            jedisCache.setByDefaultTime(key, userCart);
            jedisCache.hset(JEDIS_PREFIX_USER + "cart:list:" + userCart.getUid() + ":", id.toString(), userCart);
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改购物车商品
     *
     * @param userCart
     */
    @Override
    public String updateCart(UserCart userCart, Map<String, Object> properties) {
        return null;
    }

    /**
     * 删除购物车商品
     *
     * @param cartId
     * @param uid
     */
    @Override
    public String deleteFromCart(Long cartId, Long uid) {
        return null;
    }

    /**
     * 查询我的购物车
     *
     * @param uid
     * @param pageNum
     * @param pageSize
     */
    @Override
    public List<UserCart> getGoodsFromCart(Long uid, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 查询单条购物车详情
     *
     * @param cartId
     * @param uid
     */
    @Override
    public UserCart getCartGoodById(Long cartId, Long uid) {
        return null;
    }
}
