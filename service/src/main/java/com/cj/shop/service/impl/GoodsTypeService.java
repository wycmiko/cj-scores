package com.cj.shop.service.impl;

import com.cj.shop.api.entity.GoodsType;
import com.cj.shop.api.interf.GoodsTypeApi;
import com.cj.shop.api.param.GoodsTypeRequest;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.dao.mapper.GoodsTypeMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import com.cj.shop.service.util.ValidatorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        if (request.getParentId() != null) {
            GoodsType goodsTypeById = goodsTypeMapper.selectByPrimaryKey(request.getParentId(), "all");
            if (goodsTypeById == null) {
                return ResultMsg.PARTYPE_NOT_EXISTS;
            }
        }
        GoodsType goodsType = new GoodsType();
        goodsType.setProperties(PropertiesUtil.addProperties(request.getProperties()));
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
        GoodsType typeById = getGoodsTypeById(request.getId(), "all");
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
    public PagedList<GoodsType> getAllGoodsType(String types, Integer pageNum, Integer pageSize) {
        Page<Object> objects = null;
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        //查出顶级父类ID列表
        List<Long> ids = ValidatorUtil.checkNotEmptyList(goodsTypeMapper.selectIds(1, null, types));
        List<GoodsType> returnList = new ArrayList<>();
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                String key = JEDIS_PREFIX_GOODS_TYPE + "detail:" + id + types;
                GoodsType hget = jedisCache.get(key, GoodsType.class);
                if (hget == null) {
                    hget = getGoodsTypeById(id, types);
                }
                returnList.add(hget);
            }
        }
        PagedList<GoodsType> pagedList = new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
        return pagedList;
    }

    @Override
    public GoodsType getGoodsTypeById(Long typeId, String type) {
        String key = JEDIS_PREFIX_GOODS_TYPE + "detail:" + typeId + type;
        GoodsType goodsType = jedisCache.get(key, GoodsType.class);
        if (goodsType == null) {
            goodsType = recursiveGoodsType(typeId, type);
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
    public GoodsType recursiveGoodsType(long topId, String types) {
        GoodsType node = goodsTypeMapper.selectByPrimaryKey(topId, types);
        if (node == null) {
            return new GoodsType();
        }
        List<GoodsType> childTreeNodes = ValidatorUtil.checkNotEmptyList(goodsTypeMapper.selectAllTopsByParam(null, node.getId(), types));
        //遍历子节点
        if (!childTreeNodes.isEmpty()) {
            for (GoodsType child : childTreeNodes) {
                GoodsType n = recursiveGoodsType(child.getId(), types); //递归
                node.getSubList().add(n);
            }
        }
        return node;
    }

    /**
     * 删除指定分类
     *
     * @param typeId
     */
    @Override
    public String deleteGoodsType(Long typeId, Integer type) {
        GoodsType detailById = getGoodsTypeById(typeId, "all");
        if (detailById == null) {
            return ResultMsg.TYPE_NOT_EXISTS;
        }
        List<GoodsType> subList = detailById.getSubList();
        if (!subList.isEmpty()) {
            for (GoodsType gt : subList) {
                List<GoodsType> subList1 = gt.getSubList();
                if (!subList1.isEmpty()) {
                    for (GoodsType gt2 : subList1) {
                        goodsTypeMapper.deleteByPrimaryKey(gt2.getId(), type);
                    }
                }
            }
        }
        int i = goodsTypeMapper.deleteByPrimaryKey(typeId, type);
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
