package com.cj.shop.service.impl;

import com.cj.shop.api.entity.GoodsType;
import com.cj.shop.api.interf.GoodsTypeApi;
import com.cj.shop.api.param.GoodsTypeRequest;
import com.cj.shop.dao.mapper.GoodsTypeMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import com.cj.shop.service.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/22
 * @since 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GoodsTypeService implements GoodsTypeApi {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    @Autowired
    private JedisCache jedisCache;
    private static final String JEDIS_PREFIX_GOODS_TYPE = "cj_shop:mall:goods:type:";

    /**
     * 添加商品分类
     *
     * @param request
     */
    @Override
    public String addGoodsType(GoodsTypeRequest request) {
        GoodsType goodsType = new GoodsType();
        if (request.getProperties() == null || request.getProperties().isEmpty()) {
            goodsType.setProperties("{}");
        }
        BeanUtils.copyProperties(request, goodsType);
        int i = goodsTypeMapper.insertSelective(goodsType);
        if (i > 0) {
            delCache();
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改商品分类
     *
     * @param request
     */
    @Override
    public String updateGoodsType(GoodsTypeRequest request) {
        GoodsType typeById = getGoodsTypeById(request.getId());
        if (typeById == null) {
            return ResultMsg.TYPE_NOT_EXISTS;
        }
        GoodsType goodsType = new GoodsType();
        BeanUtils.copyProperties(request, goodsType);
        goodsType.setProperties(PropertiesUtil.changeProperties(typeById.getProperties(), request.getProperties()));
        int i = goodsTypeMapper.updateByPrimaryKeySelective(goodsType);
        if (i > 0) {
            delCache();
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询全部商品分类
     * 三级对应关系:
     * {type1 :[
     * type2 :[
     * "type3","type4"
     * ]
     * ]}
     */
    @Override
    public List<GoodsType> getAllGoodsType(Integer types) {
        String key = JEDIS_PREFIX_GOODS_TYPE + "type:list:";
        //查出顶级父类ID列表
        List<GoodsType> returnList = new ArrayList<>();
        List<Long> ids = ValidatorUtil.checkNotEmpty(goodsTypeMapper.selectIds(1, null, types));
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                GoodsType hget = jedisCache.hget(key, id.toString(), GoodsType.class);
                if (hget == null) {
                    hget = recursiveGoodsType(id, types);
                    jedisCache.hset(key,id.toString(), hget);
                }
                returnList.add(hget);
            }
        }
        return returnList;
    }

    @Override
    public GoodsType getGoodsTypeById(Long typeId) {
        String key = JEDIS_PREFIX_GOODS_TYPE + "detail:" + typeId;
        GoodsType goodsType = jedisCache.get(key, GoodsType.class);
        if (goodsType == null) {
            goodsType = recursiveGoodsType(typeId, 1);
            jedisCache.setByDefaultTime(key, goodsType);
        }
        return goodsType;
    }

    /**
     * 多级菜单：
     * 递归算法解析成树形结构
     *
     * @param topId 根节点ID
     * @return
     */
    public GoodsType recursiveGoodsType(long topId, Integer types) {
        GoodsType node = goodsTypeMapper.selectByPrimaryKey(topId);
        if (node == null) {
            return new GoodsType();
        }
        List<GoodsType> childTreeNodes = goodsTypeMapper.selectAllTopsByParam(null, node.getId(), types);
        //遍历子节点
        for (GoodsType child : childTreeNodes) {
            GoodsType n = recursiveGoodsType(child.getId(), types); //递归
            node.getSubList().add(n);
        }
        return node;
    }

    /**
     * 删除指定分类
     *
     * @param typeId
     */
    @Override
    public String deleteGoodsType(Long typeId) {
        GoodsType detailById = getGoodsTypeById(typeId);
        if (detailById == null) {
            return ResultMsg.TYPE_NOT_EXISTS;
        }
        int i = goodsTypeMapper.deleteByPrimaryKey(typeId);
        if (i > 0) {
            delCache();
        }
        return ResultMsgUtil.dmlResult(i);
    }

    private void delCache() {
        String key1 = JEDIS_PREFIX_GOODS_TYPE + "*";
        jedisCache.delWithPattern(key1);
    }
}
