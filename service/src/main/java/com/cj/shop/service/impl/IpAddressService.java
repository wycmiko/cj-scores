package com.cj.shop.service.impl;

import com.cj.shop.api.entity.IpAllow;
import com.cj.shop.dao.mapper.IpAddressMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.util.ResultMsgUtil;
import com.cj.shop.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuchuanWeng
 * @date 2018/7/20
 * @since 1.0
 */
@Slf4j
@Transactional
@Service
public class IpAddressService {
    @Autowired
    private IpAddressMapper ipAddressMapper;
    @Autowired
    private JedisCache jedisCache;

    private final String key = "cj_shop:mall:ip:";

    /**
     * 添加权限IP名单
     *
     * @param ip
     * @return
     */
    public String insertIpAllow(IpAllow ip) {
        IpAllow ipAllow = getIpAllowedDetail(ip.getIp(), 2);
        if (ipAllow != null) {
            return "Ip已存在";
        }
        int i = ipAddressMapper.addIpRecord(ip);
        if (i > 0) {
            jedisCache.hset(key, ip.getIp(), getIpAllowedDetail(ip.getIp(), 2));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    public IpAllow getIpAllowedDetail(String ip, Integer type) {
        IpAllow hget = jedisCache.hget(key, ip, IpAllow.class);
        if (hget == null) {
            hget = ipAddressMapper.selectByIp(ip, type);
            jedisCache.hset(key, ip, hget);
        }
        return hget;
    }

    public List<IpAllow> getAllIpAllows() {
        return ValidatorUtil.checkNotEmptyList(ipAddressMapper.getIpAllowedList());
    }

    public String updateIpAllow(String ip, Integer enabled) {
        IpAllow ipAllow = getIpAllowedDetail(ip, 2);
        if (ipAllow == null) {
            return "Ip不存在";
        }
        int i = ipAddressMapper.deleteByIpAddress(ip, enabled);
        if (i > 0) {
            jedisCache.hdel(key, ip);
        }
        return ResultMsgUtil.dmlResult(i);
    }
}
