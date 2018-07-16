package com.cj.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cj.shop.api.entity.*;
import com.cj.shop.api.interf.GoodsExtensionApi;
import com.cj.shop.api.param.*;
import com.cj.shop.api.param.select.StockSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.PriceLimit;
import com.cj.shop.api.response.dto.GoodsDto;
import com.cj.shop.api.response.dto.GoodsStockDto;
import com.cj.shop.dao.mapper.*;
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
import java.util.List;
import java.util.ListIterator;

/**
 * 货物扩展属性服务层 （规格、标签、单位CURD服务）
 *
 * @author yuchuanWeng()
 * @date 2018/6/25
 * @since 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class GoodsExtensionService implements GoodsExtensionApi {
    @Autowired
    private GoodsSpecMapper goodsSpecMapper;
    @Autowired
    private GoodsTagMapper goodsTagMapper;
    @Autowired
    private GoodsUnitMapper goodsUnitMapper;
    @Autowired
    private GoodsStockMapper goodsStockMapper;
    @Autowired
    private ExpressCashMapper expressCashMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private JedisCache jedisCache;
    public static final String EXPRESS_CASH_KEY = "cj_shop:mall:express:cash:";
    public static final String SPEC_KEY = "cj_shop:mall:goods:spec:";
    public static final String TAG_KEY = "cj_shop:mall:goods:tag:";
    public static final String UNIT_KEY = "cj_shop:mall:goods:unit:";
    public static final String STOCK_KEY = "cj_shop:mall:goods:stock:";


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
        int i = goodsSpecMapper.insertSelective(bloBs);
        if (i > 0) {
            jedisCache.delWithPattern(SPEC_KEY + "*");
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
        GoodsSpecWithBLOBs specDetail = getGoodsSpecDetail(request.getId(), "all");
        if (specDetail == null) {
            return ResultMsg.SPEC_NOT_EXISTS;
        }
        /**
         *
         */
        if (request.getDeleteFlag() != null) {
            //级联删除
            List<GoodsSpecWithBLOBs> subList = specDetail.getSubList();
            for (GoodsSpecWithBLOBs bs : subList) {
                bs.setDeleteFlag(request.getDeleteFlag());
                goodsSpecMapper.updateByPrimaryKeySelective(bs);
            }
        }

        BeanUtils.copyProperties(request, specDetail);
        int i = goodsSpecMapper.updateByPrimaryKeySelective(specDetail);
        if (i > 0) {
            jedisCache.delWithPattern(SPEC_KEY + "*");
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 多级菜单：
     * 递归算法解析成树形结构
     *
     * @param topId 根节点ID
     * @return
     */
    public GoodsSpecWithBLOBs recursiveGoodsSpecType(long topId, String type) {
        GoodsSpecWithBLOBs node = goodsSpecMapper.selectByPrimaryKey(topId, type);
        if (node == null) {
            return new GoodsSpecWithBLOBs();
        }
        List<Long> childTreeNodes = ValidatorUtil.checkNotEmptyList(goodsSpecMapper.selectAllSpecIds(type, node.getId(), null));
        //遍历子节点
        if (!childTreeNodes.isEmpty()) {
            for (Long child : childTreeNodes) {
                GoodsSpecWithBLOBs n = recursiveGoodsSpecType(child, type); //递归
                node.getSubList().add(n);
            }
        }
        return node;
    }

    /**
     * 查询规格详情
     *
     * @param specId
     */
    @Override
    public GoodsSpecWithBLOBs getGoodsSpecDetail(Long specId, String type) {
        GoodsSpecWithBLOBs hget = jedisCache.hget(SPEC_KEY, specId.toString(), GoodsSpecWithBLOBs.class);
        if (hget == null) {
            hget = recursiveGoodsSpecType(specId, "all");
            jedisCache.hset(SPEC_KEY, specId.toString(), hget);
        }
        if (hget != null && hget.getSubList() != null && !hget.getSubList().isEmpty()) {
            //type
            if ("exist".equals(type)) {
                ListIterator<GoodsSpecWithBLOBs> iterator = hget.getSubList().listIterator();
                while (iterator.hasNext()) {
                    GoodsSpecWithBLOBs bloBs = iterator.next();
                    if (bloBs.getDeleteFlag() == 1) {
                        iterator.remove();
                    }
                }
            }
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
        List<Long> ids = ValidatorUtil.checkNotEmptyList(goodsSpecMapper.selectAllSpecIds(type, null, 1));
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                GoodsSpecWithBLOBs specDetail = getGoodsSpecDetail(id, type);
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
        //判断是否有重名标签
        List<Long> longs = ValidatorUtil.checkNotEmptyList(goodsTagMapper.selectIDByTagName(request.getTagName()));
        if (!longs.isEmpty()) {
            return ResultMsg.TAG_ALREADY_EXISTS;
        }
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
        //判断是否有重名标签
        if (!StringUtils.isBlank(request.getTagName())) {
            List<Long> longs = ValidatorUtil.checkNotEmptyList(goodsTagMapper.selectIDByTagName(request.getTagName()));
            if (!longs.isEmpty()) {
                return ResultMsg.TAG_ALREADY_EXISTS;
            }
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
        goodsUnitDetail.setProperties(PropertiesUtil.changeProperties(goodsUnitDetail.getProperties(), request.getProperties()));
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


    public int deleteStock(String goodsNumber) {
        StockSelect stock = new StockSelect();
        stock.setGoodSn(goodsNumber);
        List<Long> ids = ValidatorUtil.checkNotEmptyList(goodsStockMapper.selectByGoodsTypeIds(stock));
        int i = goodsStockMapper.deleteByGoodsSn(goodsNumber);
        if (i > 0) {
            if (!ids.isEmpty()) {
                for (long id : ids) {
                    //delete cache
                    jedisCache.hdel(STOCK_KEY, String.valueOf(id));
                }
            }
        }
        return i;
    }


    /**
     * 添加商品库存
     *
     * @param stockRequest
     * @return
     */
    @Override
    public String insertStock(GoodsStockRequest stockRequest) {
        //避免重复添加
        GoodsStockDto goodsStockDto = goodsStockMapper.selectBySgoodId(NumberUtil.getSmallGoodsNum(stockRequest.getGoodsSn(), stockRequest.getSpecIdList()));
        if (goodsStockDto != null) {
            return ResultMsg.STOCK_ALREADY_EXISTS;
        }
        //检验规格是否存在
        if (stockRequest.getSpecIdList() != null && !stockRequest.getSpecIdList().isEmpty()) {
            for (Long specId : stockRequest.getSpecIdList()) {
                GoodsSpecWithBLOBs exist = goodsSpecMapper.selectByPrimaryKey(specId, "exist");
                if (exist == null) {
                    return ResultMsg.SPEC_NOT_EXISTS;
                }
            }
        }
        //校验商品是否存在
        double ratio = stockRequest.getWarnRatio().doubleValue();
        long num = NumberUtil.getFloorNumber(stockRequest.getStockNum(), ratio);
        //生成小商品编号
        String sn = NumberUtil.getSmallGoodsNum(stockRequest.getGoodsSn(), stockRequest.getSpecIdList());
        GoodsStock goodsStock = new GoodsStock();
        BeanUtils.copyProperties(stockRequest, goodsStock);
        goodsStock.setSpecIdList(JSON.toJSONString(stockRequest.getSpecIdList()));
        goodsStock.setSGoodsSn(sn);
        goodsStock.setWarnStockNum((int) num);
        goodsStock.setProperties(PropertiesUtil.addProperties(stockRequest.getProperties()));
        int i = goodsStockMapper.insertSelective(goodsStock);
        if (i > 0) {
            jedisCache.hdel(STOCK_KEY, goodsStock.getId().toString());
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 修改商品库存
     *
     * @param stockRequest
     */
    @Override
    public String updateStock(GoodsStockRequest stockRequest) {
        GoodsStock goodsStock = goodsStockMapper.selectByPrimaryKey(stockRequest.getId());
        if (goodsStock == null) {
            return ResultMsg.STOCK_NOT_EXISTS;
        }
        //检验规格是否存在
        String jsonString = null;
        if (stockRequest.getSpecIdList() != null && !stockRequest.getSpecIdList().isEmpty()) {
            for (Long specId : stockRequest.getSpecIdList()) {
                GoodsSpecWithBLOBs exist = goodsSpecMapper.selectByPrimaryKey(specId, "exist");
                if (exist == null) {
                    return ResultMsg.SPEC_NOT_EXISTS;
                }
            }
            jsonString = JSON.toJSONString(stockRequest.getSpecIdList());
        }
        long num = 0;
        if (stockRequest.getWarnRatio() != null) {
            double ratio = stockRequest.getWarnRatio().doubleValue();
            num = NumberUtil.getFloorNumber(stockRequest.getStockNum(), ratio);
        }
        GoodsDto dto = goodsMapper.selectIdByGoodsSn(goodsStock.getGoodsSn());
        BeanUtils.copyProperties(stockRequest, goodsStock);
        goodsStock.setProperties(PropertiesUtil.changeProperties(goodsStock.getProperties(), stockRequest.getProperties()));
        goodsStock.setWarnStockNum((int) num);
        goodsStock.setSpecIdList(jsonString);
        int i = goodsStockMapper.updateByPrimaryKeySelective(goodsStock);
        if (i > 0) {
            //级联更新父类商品
            if (stockRequest.getStockNum() != null || stockRequest.getSellPrice() != null || stockRequest.getCostPrice() != null) {
                GoodsWithBLOBs request = new GoodsWithBLOBs();
                Integer stockNum = goodsStockMapper.getTotalStockNum(dto.getGoodsSn());
                request.setId(dto.getId());
                request.setStockNum(stockNum);
                PriceLimit priceLimit = goodsStockMapper.getPriceLimit(dto.getGoodsSn());
                request.setMinCostPrice(priceLimit.getMinCostPrice());
                request.setMaxCostPrice(priceLimit.getMaxCostPrice());
                request.setMinSellPrice(priceLimit.getMinSellPrice());
                request.setMaxSellPrice(priceLimit.getMaxSellPrice());
                int i1 = goodsMapper.updateByPrimaryKeySelective(request);
                log.info("update Goods Stock Num(id={},stockNum={}) Result={}", dto.getId(), stockNum, i1 > 0);
                jedisCache.hdel(GoodsService.JEDIS_PREFIX_GOODS, dto.getId().toString());
            }
            jedisCache.hdel(STOCK_KEY, stockRequest.getId().toString());
            jedisCache.hdel(GoodsService.JEDIS_PREFIX_GOODS, dto.getId().toString());
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * type=1 增加
     * type=2 减少 相应的库存数量
     *
     * @param sGoodSn 小商品编号
     * @param type
     * @param num     商品数量
     * @return
     */
    public String updateStockNum(String sGoodSn, int type, int num) {
        GoodsStockDto stockDto = goodsStockMapper.selectBySgoodId(sGoodSn);
        if (stockDto == null) {
            return ResultMsg.STOCK_NOT_EXISTS;
        }
        int i = goodsStockMapper.updateGoodsStock(sGoodSn, type, num);
        if (i > 0) {
            GoodsWithBLOBs request = new GoodsWithBLOBs();
            Integer stockNum = goodsStockMapper.getTotalStockNum(stockDto.getGoodsSn());
            request.setId(stockDto.getGoodsId());
            request.setStockNum(stockNum);
            goodsMapper.updateByPrimaryKeySelective(request);
            jedisCache.hdel(STOCK_KEY, stockDto.getStockId().toString());
            jedisCache.hdel(GoodsService.JEDIS_PREFIX_GOODS, stockDto.getGoodsId().toString());
            jedisCache.hset(STOCK_KEY, stockDto.getStockId().toString(), getStockById(stockDto.getStockId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询全部商品库存
     *
     * @param request
     */
    @Override
    public PagedList<GoodsStockDto> findAllGoodsStock(StockSelect request) {
        Page<Object> objects = null;
        List<GoodsStockDto> list = new ArrayList<>();
        if (request.getPageNum() != null && request.getPageSize() != null) {
            objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
        } else {
            request.setPageNum(0);
            request.setPageSize(0);
        }
        List<Long> longList = ValidatorUtil.checkNotEmptyList(goodsStockMapper.selectByGoodsTypeIds(request));
        if (!longList.isEmpty()) {
            for (Long id : longList) {
                GoodsStockDto dto = getStockById(id);
                list.add(dto);
            }
        }
        PagedList<GoodsStockDto> pagedList = new PagedList<>(list, objects == null ? 0 : objects.getTotal(), request.getPageNum(), request.getPageSize());
        return pagedList;
    }

    /**
     * 查询库存详细
     *
     * @param id
     * @return
     */
    @Override
    public GoodsStockDto getStockById(Long id) {
        GoodsStockDto hget = jedisCache.hget(STOCK_KEY, id.toString(), GoodsStockDto.class);
        if (hget == null) {
            hget = goodsStockMapper.selectByGoodsType(id);
            if (hget != null) {
                if (hget.getWarnStockNum() < hget.getStockNum()) {
                    //库存充足
                    hget.setWarnStock(2);
                } else if (hget.getWarnStockNum() >= hget.getStockNum()) {
                    hget.setWarnStock(1);
                } else if (hget.getStockNum() == 0) {
                    hget.setWarnStock(0);
                }
                List<Long> longList = JSON.parseArray(hget.getSpecIdList(), Long.class);
                List<GoodsSpecWithBLOBs> list = new ArrayList<>();
                if (longList != null && !longList.isEmpty()) {
                    for (Long id1 : longList) {
                        GoodsSpecWithBLOBs detail = getGoodsSpecDetail(id1, "all");
                        if (detail != null && detail.getParentId() != null) {
                            GoodsSpecWithBLOBs detail1 = getGoodsSpecDetail(detail.getParentId(), "all");
                            if (detail1 != null)
                                detail.setParentName(detail1.getSpecName());
                        }
                        list.add(detail);
                    }
                }
                hget.setSpecList(list);
                GoodsSupply detail = goodsService.getSupplyDetail(hget.getSupplyId());
                hget.setSupplyName(detail == null ? null : detail.getSupplyName());
            }
            jedisCache.hset(STOCK_KEY, id.toString(), hget);
        }
        return hget;
    }


    public String addGloableExpressCash(ExpressCash expressCash) {
        if (expressCash.getDeliveryCash() < 0) {
            return ResultMsg.MUST_POSITIVE_NUM;
        }
        expressCash.setId(1L);
        int i = expressCashMapper.insertExpressCash(expressCash);
        if (i > 0) {
            jedisCache.hset(EXPRESS_CASH_KEY, "1", expressCashMapper.selectByPrimaryKey(1L));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    public ExpressCash getExpressCash() {
        ExpressCash hget = jedisCache.hget(EXPRESS_CASH_KEY, "1", ExpressCash.class);
        if (hget == null) {
            hget = expressCashMapper.selectByPrimaryKey(1L);
            jedisCache.hset(EXPRESS_CASH_KEY, "1", hget);
        }
        return hget;
    }
}
