package com.cj.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cj.shop.api.entity.*;
import com.cj.shop.api.interf.GoodsApi;
import com.cj.shop.api.param.GoodsBrandRequest;
import com.cj.shop.api.param.GoodsRequest;
import com.cj.shop.api.param.GoodsStockRequest;
import com.cj.shop.api.param.GoodsSupplyRequest;
import com.cj.shop.api.param.select.GoodsSelect;
import com.cj.shop.api.param.select.StockSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.PriceLimit;
import com.cj.shop.api.response.dto.GoodsDto;
import com.cj.shop.api.response.dto.GoodsStockDto;
import com.cj.shop.common.utils.DateUtils;
import com.cj.shop.dao.mapper.GoodsBrandMapper;
import com.cj.shop.dao.mapper.GoodsMapper;
import com.cj.shop.dao.mapper.GoodsStockMapper;
import com.cj.shop.dao.mapper.GoodsSupplyMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.util.NumberUtil;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import com.cj.shop.service.util.ValidatorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 商品service
 *
 * @author yuchuanWeng()
 * @date 2018/6/25
 * @since 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
public class GoodsService implements GoodsApi {
    @Autowired
    private JedisCache jedisCache;
    @Autowired
    private GoodsSupplyMapper goodsSupplyMapper;
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    @Autowired
    private GoodsExtensionService goodsExtensionService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private GoodsStockMapper goodsStockMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    //non-fair lock
    private static final ReentrantLock lock = new ReentrantLock();

    public static final String JEDIS_PREFIX_GOODS = "cj_shop:mall:goods:";
    public static final String JEDIS_PREFIX_GOODS_SUPPLY = JEDIS_PREFIX_GOODS + "supply:";
    public static final String JEDIS_PREFIX_GOODS_BRAND = JEDIS_PREFIX_GOODS + "brand:";

    /**
     * 添加供应商
     *
     * @param request
     * @return
     */
    @Override
    public String insertSupply(GoodsSupplyRequest request) {
        GoodsSupply supply = new GoodsSupply();
        BeanUtils.copyProperties(request, supply);
        supply.setProperties(PropertiesUtil.addProperties(request.getProperties()));
        int i = goodsSupplyMapper.insertSelective(supply);
        if (i > 0) {
            jedisCache.hset(JEDIS_PREFIX_GOODS_SUPPLY, supply.getId().toString(), goodsBrandMapper.selectByPrimaryKey(supply.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改供应商(包含禁用)
     *
     * @param request
     * @return
     */
    @Override
    public String updateSupply(GoodsSupplyRequest request) {
        GoodsSupply supplyDetail = goodsSupplyMapper.selectByPrimaryKey(request.getId());
        if (supplyDetail == null) {
            return ResultMsg.SUPPLY_NOT_EXISTS;
        }
        supplyDetail.setProperties(PropertiesUtil.changeProperties(supplyDetail.getProperties(), request.getProperties()));
        BeanUtils.copyProperties(request, supplyDetail);
        int i = goodsSupplyMapper.updateByPrimaryKeySelective(supplyDetail);
        if (i > 0) {
            jedisCache.hset(JEDIS_PREFIX_GOODS_SUPPLY, request.getId().toString(), goodsSupplyMapper.selectByPrimaryKey(request.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询全部供应商
     *
     * @param supplyName 供应商名称模糊匹配
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<GoodsSupply> findAllSupplies(String supplyName, Integer pageNum, Integer pageSize, String type) {
        Integer flag = null;
        if (!"all".equals(type.toLowerCase())) {
            flag = 1;
        }
        Page<Object> objects = null;
        List<GoodsSupply> list = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        List<Long> longs = ValidatorUtil.checkNotEmptyList(goodsSupplyMapper.selectAllSupplyIds(supplyName, flag));
        if (!longs.isEmpty()) {

            for (Long id : longs) {
                GoodsSupply supplyDetail = getSupplyDetail(id);
                list.add(supplyDetail);
            }
        }
        PagedList<GoodsSupply> pagedList = new PagedList<>(list, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
        return pagedList;
    }

    /**
     * 查询供应商明细
     *
     * @param supplyId
     * @return
     */
    @Override
    public GoodsSupply getSupplyDetail(Long supplyId) {
        GoodsSupply hget = jedisCache.hget(JEDIS_PREFIX_GOODS_SUPPLY, supplyId.toString(), GoodsSupply.class);
        if (hget == null) {
            hget = goodsSupplyMapper.selectByPrimaryKey(supplyId);
            jedisCache.hset(JEDIS_PREFIX_GOODS_SUPPLY, supplyId.toString(), hget);
        }
        return hget;
    }

    /**
     * 添加品牌
     *
     * @param request
     * @return
     */
    @Override
    public String insertBrand(GoodsBrandRequest request) {
        GoodsBrand brand = new GoodsBrand();
        BeanUtils.copyProperties(request, brand);
        brand.setProperties(PropertiesUtil.addProperties(request.getProperties()));
        int i = goodsBrandMapper.insertSelective(brand);
        if (i > 0) {
            jedisCache.hset(JEDIS_PREFIX_GOODS_BRAND, brand.getId().toString(), goodsBrandMapper.selectByPrimaryKey(brand.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改品牌
     *
     * @param request
     * @return
     */
    @Override
    public String updateBrand(GoodsBrandRequest request) {
        GoodsBrand brandDetail = getBrandDetail(request.getId());
        if (brandDetail == null) {
            return ResultMsg.BRAND_NOT_EXISTS;
        }
        GoodsBrand brand = new GoodsBrand();
        BeanUtils.copyProperties(request, brand);
        brand.setProperties(PropertiesUtil.changeProperties(brandDetail.getProperties(), request.getProperties()));
        int i = goodsBrandMapper.updateByPrimaryKeySelective(brand);
        if (i > 0) {
            jedisCache.hset(JEDIS_PREFIX_GOODS_BRAND, request.getId().toString(), goodsBrandMapper.selectByPrimaryKey(request.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询品牌详情
     *
     * @param brandId
     * @return
     */
    @Override
    public GoodsBrand getBrandDetail(Long brandId) {
        GoodsBrand hget = jedisCache.hget(JEDIS_PREFIX_GOODS_BRAND, brandId.toString(), GoodsBrand.class);
        if (hget == null) {
            hget = goodsBrandMapper.selectByPrimaryKey(brandId);
            jedisCache.hset(JEDIS_PREFIX_GOODS_BRAND, brandId.toString(), hget);
        }
        return hget;
    }

    /**
     * 查询全部品牌
     *
     * @param brandName 品牌名模糊匹配
     * @param pageNum   当前页
     * @param pageSize  每页条数
     * @param type      类型 all=显示全部 其它显示未被禁用的
     * @return
     */
    @Override
    public PagedList<GoodsBrand> findAllBrands(String brandName, Integer pageNum, Integer pageSize, String type) {
        Integer flag = null;
        if (!StringUtils.isBlank(brandName)) {
            brandName = brandName.replaceAll(" ", "");
        }
        if (!"all".equals(type.toLowerCase())) {
            flag = 1;
        }
        Page<Object> objects = null;
        List<GoodsBrand> list = null;
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        List<Long> longs = ValidatorUtil.checkNotEmptyList(goodsBrandMapper.selectBrandIds(brandName, flag));
        if (!longs.isEmpty()) {
            list = new ArrayList<>();
            for (Long id : longs) {
                GoodsBrand brandDetail = getBrandDetail(id);
                list.add(brandDetail);
            }
        }
        PagedList<GoodsBrand> pagedList = new PagedList<>(list, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
        return pagedList;
    }

    /**
     * 添加商品
     *
     * @param request
     */
    @Override
    public String insertGood(GoodsRequest request) {
        int i;
        try {
            //EC校验
            if (getBrandDetail(request.getBrandId()) == null) return ResultMsg.BRAND_NOT_EXISTS;
            if (getSupplyDetail(request.getSupplyId()) == null) return ResultMsg.SUPPLY_NOT_EXISTS;
            if (goodsExtensionService.getGoodsUnitDetail(request.getUnitId()) == null) return ResultMsg.UNIT_NOT_EXISTS;
            List<GoodsStockRequest> specList = request.getStockList();
            if (specList == null || specList.isEmpty()) {
                return ResultMsg.SPEC_LIST_EMPTY;
            }
            if (request.getGoodsName().length() >= 26) {
                return ResultMsg.GOOD_NAME_TOO_LONG;
            }
            lock.lock();
            final String goodsSn = NumberUtil.getGoodsNum();
            //添加商品规格库存列表
            for (GoodsStockRequest request1 : specList) {
                //小商品编号
                String smallNum = NumberUtil.getSmallGoodsNum(goodsSn, request1.getSpecIdList());
                request1.setGoodsSn(goodsSn);
                request1.setSGoodsSn(smallNum);

                //判断是否已经存在该类商品库存
                GoodsStockDto goodsStockDto = goodsStockMapper.selectBySgoodId(smallNum);
                if (goodsStockDto == null) {
                    String s = goodsExtensionService.insertStock(request1);
                    if (!ResultMsg.HANDLER_SUCCESS.equals(s)) {
                        //roll back
                        insertFailRollBack(s, goodsSn);
                        return s;
                    }
                }
            }
            GoodsWithBLOBs bloBs = new GoodsWithBLOBs();
            BeanUtils.copyProperties(request, bloBs);
            bloBs = updateGoosProp(request, bloBs, goodsSn);
            bloBs.setGoodDesc(PropertiesUtil.addProperties(request.getGoodDesc()));
            bloBs.setKeyWords(PropertiesUtil.addProperties(request.getKeyWords()));
            bloBs.setProperties(PropertiesUtil.addProperties(request.getProperties()));
            i = goodsMapper.insertSelective(bloBs);
            if (i > 0) {
                //添加成功 加入缓存
                jedisCache.hdel(JEDIS_PREFIX_GOODS, bloBs.getId().toString());
            } else {
                //添加失败-补偿事务
                insertFailRollBack("添加失败", goodsSn);
            }
        } finally {
            lock.unlock();
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 添加商品失败事务回滚
     *
     * @param msg 失败提示信息
     * @param num
     */
    private void insertFailRollBack(String msg, String num) {
        int i1 = goodsExtensionService.deleteStock(num);
        //操作失败 回滚 打印
        log.error("insert good stock fail {}, roll back rows={}", msg, i1);
    }

    /**
     * 用于添加、修改时候更新商品属性
     *
     * @param request
     * @param bloBs
     * @param goodsSn
     * @return
     */
    private GoodsWithBLOBs updateGoosProp(GoodsRequest request, GoodsWithBLOBs bloBs, String goodsSn) {
        if (bloBs == null) bloBs = new GoodsWithBLOBs();
        //如果为上架商品
        if (request.getSaleFlag() != null && request.getSaleFlag() == 1) {
            bloBs.setSaleTime(DateUtils.getCommonString());
        }
        PriceLimit priceLimit = goodsStockMapper.getPriceLimit(goodsSn);
        bloBs.setGoodsSn(goodsSn);
        bloBs.setMinCostPrice(priceLimit.getMinCostPrice());
        bloBs.setMaxCostPrice(priceLimit.getMaxCostPrice());
        bloBs.setMinSellPrice(priceLimit.getMinSellPrice());
        bloBs.setMaxSellPrice(priceLimit.getMaxSellPrice());
        Integer stockNum = goodsStockMapper.getTotalStockNum(goodsSn);
        bloBs.setStockNum(stockNum);
        bloBs.setWarnStock(0);
        if (stockNum == 0) {
            bloBs.setWarnStockFlag(3);
        } else {
            bloBs.setWarnStockFlag(1);
        }
        return bloBs;
    }

    /**
     * 修改商品
     *
     * @param request
     * @return
     */
    @Override
    public String updateGood(GoodsRequest request) {
        GoodsDto goodsDetail = getGoodsDetail(request.getId());
        if (goodsDetail == null) return ResultMsg.GOOD_NOT_EXISTS;
        List<GoodsStockRequest> stockList = request.getStockList();
        if (stockList != null && !stockList.isEmpty()) {
            //过滤null值
            stockList = stockList.stream().filter(x-> x.getStockNum() != null).collect(Collectors.toList());
            //添加商品规格库存列表
            for (GoodsStockRequest request1 : stockList) {
                //小商品编号
                String smallNum = NumberUtil.getSmallGoodsNum(goodsDetail.getGoodsSn(), request1.getSpecIdList());
                request1.setGoodsSn(goodsDetail.getGoodsSn());
                request1.setSGoodsSn(smallNum);
                request1.setSaleFlag(request.getSaleFlag());
                //判断是否已经存在该类商品库存
                GoodsStockDto goodsStockDto = goodsStockMapper.selectBySgoodId(smallNum);
                if (goodsStockDto == null) {
                    String s = goodsExtensionService.insertStock(request1);
                    if (!ResultMsg.HANDLER_SUCCESS.equals(s)) {
                        log.info("update Goods-insert stock failure");
                        //roll back
                        insertFailRollBack(s, goodsDetail.getGoodsSn());
                        return s;
                    }
                } else {
                    //更新操作
                    request1.setId(goodsStockDto.getStockId());
                    String s = goodsExtensionService.updateStock(request1);
                    if (!ResultMsg.HANDLER_SUCCESS.equals(s)) {
                        log.info("update Goods-insert stock failure");
                        //roll back
                        insertFailRollBack(s, goodsDetail.getGoodsSn());
                        return s;
                    }

                }
            }
        }
        //如果大商品下架 关联小商品也下架
        if (request.getSaleFlag() != null) {
            StockSelect select = new StockSelect();
            select.setType("all");
            select.setGoodSn(goodsDetail.getGoodsSn());
            List<GoodsStockDto> list = goodsExtensionService.findAllGoodsStock(select).getList();
            if (list != null && !list.isEmpty()) {
                for (GoodsStockDto dto : list) {
                    GoodsStockRequest request1 = new GoodsStockRequest();
                    request1.setId(dto.getStockId());
                    request1.setSaleFlag(request.getSaleFlag());
                    goodsExtensionService.updateStock(request1);
                }
                log.info("update stock goods cascade");
            }
        }
        GoodsWithBLOBs goodsWithBLOBs = new GoodsWithBLOBs();
        BeanUtils.copyProperties(request, goodsWithBLOBs);
        goodsWithBLOBs = updateGoosProp(request, goodsWithBLOBs, goodsDetail.getGoodsSn());
        goodsWithBLOBs.setProperties(PropertiesUtil.changeProperties(goodsDetail.getProperties(), request.getProperties()));
        goodsWithBLOBs.setGoodDesc(PropertiesUtil.changeProperties(goodsDetail.getGoodDesc(), request.getGoodDesc()));
        goodsWithBLOBs.setKeyWords(PropertiesUtil.changeProperties(goodsDetail.getKeyWords(), request.getKeyWords()));
        int i = goodsMapper.updateByPrimaryKeySelective(goodsWithBLOBs);
        if (i > 0) {
            log.info("update goods success");
            jedisCache.hdel(JEDIS_PREFIX_GOODS, request.getId().toString());
        }
        return ResultMsgUtil.dmlResult(i);
    }

    public String increPv(Long goodsId) {
        int i = goodsMapper.increPv(goodsId);
//        if (i > 0) {
//            jedisCache.hdel(JEDIS_PREFIX_GOODS, goodsId.toString());
//        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 复合条件查询全部商品
     *
     * @param select
     */
    @Override
    public PagedList<GoodsDto> getAllGoods(GoodsSelect select) {
        Page<Object> objects = null;
        List<GoodsDto> list = new ArrayList<>();
        if (select.getPageNum() != null && select.getPageSize() != null) {
            objects = PageHelper.startPage(select.getPageNum(), select.getPageSize());
        } else {
            select.setPageNum(0);
            select.setPageSize(0);
        }
        List<Long> longs = ValidatorUtil.checkNotEmptyList(goodsMapper.selectGoodsIds(select));
        if (!longs.isEmpty()) {
            for (Long id : longs) {
                GoodsDto goodsDetail = getGoodsDetail(id);
                list.add(goodsDetail);
            }
        }
        PagedList<GoodsDto> pagedList = new PagedList<>(list, objects == null ? 0 : objects.getTotal(), select.getPageNum(), select.getPageSize());
        return pagedList;
    }

    /**
     * 查询单条商品明细
     *
     * @param goodsId
     */
    @Override
    public GoodsDto getGoodsDetail(Long goodsId) {
        GoodsDto hget = jedisCache.hget(JEDIS_PREFIX_GOODS, goodsId.toString(), GoodsDto.class);
        if (hget == null) {
            hget = getCompletGoods(goodsId);
            jedisCache.hset(JEDIS_PREFIX_GOODS, goodsId.toString(), hget);
        }
        //设置运费
        if (hget != null) {
            ExpressCash cash = goodsExtensionService.getExpressCash();
            hget.setExpressCash(cash == null ? 0.0 : cash.getDeliveryCash());
        }
        return hget;
    }

    public GoodsDto getGoodsDetailForIndex(Long goodsId) {
        GoodsDto hget = getGoodsDetail(goodsId);
        if (hget == null || hget.getDeleteFlag() == 1) {
            return null;
        }
        return hget;
    }

    private GoodsDto getCompletGoods(Long goodsId) {
        GoodsDto dto = goodsMapper.selectByPrimaryKey(goodsId);
        if (dto != null) {
            Integer stockNum = goodsStockMapper.getTotalStockNum(dto.getGoodsSn());
            //判断库存
            stockNum = stockNum == null ? 0 : stockNum;
            dto.setStockNum(stockNum);
            if (stockNum == 0) {
                dto.setWarnStockFlag(3);
            } else {
                dto.setWarnStockFlag(1);
            }
            if (dto.getSaleFlag() == 1) {
                dto.setSaleFlagDesc("上架销售中");
            } else {
                dto.setSaleFlagDesc("已下架");
            }
            //分类添加
            GoodsType first = goodsTypeService.getGoodsTypeById(dto.getFirstTypeId(), "all");
            if (first != null) {
                dto.setFirstTypeName(first.getTypeName());
            }
            GoodsType second = goodsTypeService.getGoodsTypeById(dto.getSecondTypeId(), "all");
            if (second != null) {
                dto.setSecondTypeName(second.getTypeName());
            }
            GoodsType thrid = goodsTypeService.getGoodsTypeById(dto.getThirdTypeId(), "all");
            if (thrid != null) {
                dto.setThirdTypeName(thrid.getTypeName());
            }
            dto.setShopName("珑讯自营");
            List<GoodsTag> goodsTagList = new ArrayList<>();
            String tag_id_list = JSON.parseObject(dto.getKeyWords()).getString("tag_id_list");
            if (!StringUtils.isBlank(tag_id_list)) {
                List<Long> tagIds = JSON.parseArray(tag_id_list, Long.class);
                if (tagIds != null)
                    for (Long id : tagIds) {
                        GoodsTag goodsTagDetail = goodsExtensionService.getGoodsTagDetail(id);
                        goodsTagList.add(goodsTagDetail);
                    }
            }
            StockSelect select = new StockSelect();
            select.setGoodSn(dto.getGoodsSn());
            PagedList<GoodsStockDto> allGoodsStock = goodsExtensionService.findAllGoodsStock(select);
            List<GoodsStockDto> list = allGoodsStock.getList();
            dto.setStockList(list);
            //封装Spec列表
            if (!list.isEmpty()) {
                List<GoodsSpecWithBLOBs> spec = new ArrayList<>();
                for (GoodsStockDto dt : list) {
                    List<GoodsSpecWithBLOBs> specList = dt.getSpecList();
                    if (specList != null && !specList.isEmpty()) {
                        spec.addAll(specList);
                    }
                }
                //第二个参数表示指定一个Map 第三个参数分组后如何收集
                LinkedHashMap<String, List<GoodsSpecWithBLOBs>> collect = spec.stream()
                        .filter(PropertiesUtil.distinctByKey(o -> o.getSpecName()))
                        .collect(Collectors.groupingBy(GoodsSpecWithBLOBs::getParentName, LinkedHashMap::new, Collectors.toList()));
                dto.setSpecArray(collect);
                //封装json- spec_name_array给前台
                if (collect != null && !collect.isEmpty()) {
                    dto.setSpecNameArray(collect.keySet().stream().collect(Collectors.toList()));
                }
            }
            dto.setTagList(goodsTagList);
        }
        return dto;
    }




}
