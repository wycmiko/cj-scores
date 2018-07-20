package com.cj.shop.dao.mapper;


import com.cj.shop.api.entity.IpAllow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mybatis自动生成Mapper
 *
 * @author yuchuanWeng
 * @version 1.0
 * @date 2018-06-02
 */
@Mapper
public interface IpAddressMapper {
    /**
     * 添加文件上传记录
     */
    int addIpRecord(IpAllow ipAllow);

    //type=1 exist数据  2=all
    IpAllow selectByIp(@Param("ip") String ip, @Param("type") Integer type);

    List<IpAllow> getIpAllowedList();

    int deleteByIpAddress(@Param("ip") String ip, @Param("enabled") Integer enabled);

}