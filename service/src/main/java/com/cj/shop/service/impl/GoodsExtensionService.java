package com.cj.shop.service.impl;

import com.cj.shop.api.entity.GoodsSpecWithBLOBs;
import com.cj.shop.api.entity.GoodsTag;
import com.cj.shop.api.entity.GoodsUnit;
import com.cj.shop.api.interf.GoodsExtensionApi;
import com.cj.shop.api.param.GoodsSpecRequest;
import com.cj.shop.api.param.GoodsTagRequest;
import com.cj.shop.api.param.GoodsUnitRequest;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.dao.mapper.GoodsSpecMapper;
import com.cj.shop.dao.mapper.GoodsTagMapper;
import com.cj.shop.dao.mapper.GoodsUnitMapper;
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
 * 货物扩展属性服务层 （规格、标签、单位CURD服务）
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/25
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsExtensionService implements GoodsExtensionApi {
    @Autowired
    private GoodsSpecMapper goodsSpecMapper;
    @Autowired
    private GoodsTagMapper goodsTagMapper;
    @Autowired
    private GoodsUnitMapper goodsUnitMapper;

    @Autowired
    private JedisCache jedisCache;
    private static final String SPEC_KEY = "cj_shop:mall:goods:spec:";
    private static final String TAG_KEY = "cj_shop:mall:goods:tag:";
    private static final String UNIT_KEY = "cj_shop:mall:goods:unit:";

    /**
     * 添加商品规格
     *
     * @param request
     * @return
     */
    @Override
    public String insertGoodsSpec(GoodsSpecRequest request) {
        GoodsSpecWithBLOBs bloBs = new GoodsSpecWithBLOBs();
        BeanUtils.copyProperties(request, bloBs);
        bloBs.setSpecProperties(PropertiesUtil.addProperties(request.getSpecProperties()));
        bloBs.setSizeProperties(PropertiesUtil.addProperties(request.getSizeProperties()));
        bloBs.setProperties(PropertiesUtil.addProperties(request.getProperties()));
        int i = goodsSpecMapper.insertSelective(bloBs);
        if (i > 0) {
            jedisCache.hset(SPEC_KEY, bloBs.getId().toString(), goodsSpecMapper.selectByPrimaryKey(bloBs.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改商品规格
     *
     * @param request
     */
    @Override
    public String updateGoodsSpec(GoodsSpecRequest request) {
        GoodsSpecWithBLOBs specDetail = getGoodsSpecDetail(request.getId());
        if (specDetail == null) {
            return ResultMsg.SPEC_NOT_EXISTS;
        }
        BeanUtils.copyProperties(request, specDetail);
        specDetail.setSpecProperties(PropertiesUtil.changeProperties(specDetail.getSpecProperties(), request.getSpecProperties()));
        specDetail.setSizeProperties(PropertiesUtil.changeProperties(specDetail.getSizeProperties(), request.getSizeProperties()));
        specDetail.setProperties(PropertiesUtil.changeProperties(specDetail.getProperties(), request.getProperties()));
        int i = goodsSpecMapper.updateByPrimaryKeySelective(specDetail);
        if (i > 0) {
            jedisCache.hset(SPEC_KEY, request.getId().toString(), goodsSpecMapper.selectByPrimaryKey(request.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询规格详情
     *
     * @param specId
     */
    @Override
    public GoodsSpecWithBLOBs getGoodsSpecDetail(Long specId) {
        GoodsSpecWithBLOBs hget = jedisCache.hget(SPEC_KEY, specId.toString(), GoodsSpecWithBLOBs.class);
        if (hget == null) {
            hget = goodsSpecMapper.selectByPrimaryKey(specId);
            jedisCache.hset(SPEC_KEY, specId.toString(), hget);
        }
        return hget;
    }

    /**
     * 查询全部规格
     *
     * @param pageNum
     * @param pageSize
     * @param type     all 显示全部(包含禁用) exist(显示)
     * @return
     */
    @Override
    public PagedList<GoodsSpecWithBLOBs> findAllSpecs(Integer pageNum, Integer pageSize, String type) {
        Page<Object> objects = null;
        List<GoodsSpecWithBLOBs> returnList = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        List<Long> ids = ValidatorUtil.checkNotEmptyList(goodsSpecMapper.selectAllSpecIds(type));
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                GoodsSpecWithBLOBs specDetail = getGoodsSpecDetail(id);
                returnList.add(specDetail);
            }
        }
        PagedList<GoodsSpecWithBLOBs> pagedList = new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
        return pagedList;
    }

    /**
     * 添加商品标签
     *
     * @param request
     * @return
     */
    @Override
    public String insertGoodsTag(GoodsTagRequest request) {
        GoodsTag tag = new GoodsTag();
        BeanUtils.copyProperties(request, tag);
        tag.setProperties(PropertiesUtil.addProperties(request.getProperties()));
        int i = goodsTagMapper.insertSelective(tag);
        if (i > 0) {
            jedisCache.hset(TAG_KEY, tag.getId().toString(), goodsTagMapper.selectByPrimaryKey(tag.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改商品标签
     *
     * @param request
     */
    @Override
    public String updateGoodsTag(GoodsTagRequest request) {
        GoodsTag tagDetail = getGoodsTagDetail(request.getId());
        if (tagDetail == null) {
            return ResultMsg.TAG_NOT_EXISTS;
        }
        BeanUtils.copyProperties(request, tagDetail);
        tagDetail.setProperties(PropertiesUtil.changeProperties(tagDetail.getProperties(), request.getProperties()));
        int i = goodsTagMapper.updateByPrimaryKeySelective(tagDetail);
        if (i > 0) {
            jedisCache.hset(TAG_KEY, request.getId().toString(), goodsTagMapper.selectByPrimaryKey(request.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询商品标签
     *
     * @param tagId
     */
    @Override
    public GoodsTag getGoodsTagDetail(Long tagId) {
        GoodsTag hget = jedisCache.hget(TAG_KEY, tagId.toString(), GoodsTag.class);
        if (hget == null) {
            hget = goodsTagMapper.selectByPrimaryKey(tagId);
            jedisCache.hset(TAG_KEY, tagId.toString(), hget);
        }
        return hget;
    }

    /**
     * 查询全部标签
     *
     * @param pageNum
     * @param pageSize
     * @param type     all 显示全部(包含禁用) exist(显示未被删除的)
     * @return
     */
    @Override
    public PagedList<GoodsTag> findAllTags(Integer pageNum, Integer pageSize, String type) {
        Page<Object> objects = null;
        List<GoodsTag> returnList = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        List<Long> ids = ValidatorUtil.checkNotEmptyList(goodsTagMapper.selectTagIds(type));
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                GoodsTag goodsTag = getGoodsTagDetail(id);
                returnList.add(goodsTag);
            }
        }
        PagedList<GoodsTag> pagedList = new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
        return pagedList;
    }

    /**
     * 添加计量单位
     *
     * @param request
     * @return
     */
    @Override
    public String insertGoodsUnit(GoodsUnitRequest request) {
        GoodsUnit goodsUnit = new GoodsUnit();
        BeanUtils.copyProperties(request, goodsUnit);
        goodsUnit.setProperties(PropertiesUtil.addProperties(request.getProperties()));
        int i = goodsUnitMapper.insertSelective(goodsUnit);
        if (i > 0) {
            jedisCache.hset(UNIT_KEY, goodsUnit.getId().toString(), goodsUnitMapper.selectByPrimaryKey(goodsUnit.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改计量单位
     *
     * @param request
     */
    @Override
    public String updateGoodsUnit(GoodsUnitRequest request) {
        GoodsUnit goodsUnitDetail = getGoodsUnitDetail(request.getId());
        if (goodsUnitDetail == null) {
            return ResultMsg.UNIT_NOT_EXISTS;
        }
        BeanUtils.copyProperties(request, goodsUnitDetail);
        int i = goodsUnitMapper.updateByPrimaryKeySelective(goodsUnitDetail);
        if (i > 0) {
            jedisCache.hset(UNIT_KEY, request.getId().toString(), goodsUnitMapper.selectByPrimaryKey(request.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询计量单位详情
     *
     * @param unitId
     */
    @Override
    public GoodsUnit getGoodsUnitDetail(Long unitId) {
        GoodsUnit goodsUnit = jedisCache.hget(UNIT_KEY, unitId.toString(), GoodsUnit.class);
        if (goodsUnit == null) {
            goodsUnit = goodsUnitMapper.selectByPrimaryKey(unitId);
            jedisCache.hset(UNIT_KEY, unitId.toString(), goodsUnit);
        }
        return goodsUnit;
    }

    /**
     * 查询全部计量单位
     *
     * @param pageNum
     * @param pageSize
     * @param type     all 显示全部(包含禁用) exist(显示未被删除的)
     * @return
     */
    @Override
    public PagedList<GoodsUnit> findAllUnits(Integer pageNum, Integer pageSize, String type) {
        Page<Object> objects = null;
        List<GoodsUnit> returnList = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        List<Long> ids = ValidatorUtil.checkNotEmptyList(goodsUnitMapper.selectUnitIds(type));
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                GoodsUnit goodsUnit = getGoodsUnitDetail(id);
                returnList.add(goodsUnit);
            }
        }
        PagedList<GoodsUnit> pagedList = new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
        return pagedList;
    }
}
