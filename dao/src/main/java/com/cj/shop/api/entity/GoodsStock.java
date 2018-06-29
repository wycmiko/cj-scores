package com.cj.shop.api.entity;

import java.util.Date;

public class GoodsStock {
    private Long id;

    private String goodsSn;

    private String sGoodsSn;

    private String specName;

    private Integer stockNum;

    private Integer warnStockNum;

    private Boolean warnStock;

    private Boolean deleteFlag;

    private Date updateTime;

    private Date createTime;

    private Integer sortFlag;

    private String properties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
    }

    public String getsGoodsSn() {
        return sGoodsSn;
    }

    public void setsGoodsSn(String sGoodsSn) {
        this.sGoodsSn = sGoodsSn == null ? null : sGoodsSn.trim();
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getWarnStockNum() {
        return warnStockNum;
    }

    public void setWarnStockNum(Integer warnStockNum) {
        this.warnStockNum = warnStockNum;
    }

    public Boolean getWarnStock() {
        return warnStock;
    }

    public void setWarnStock(Boolean warnStock) {
        this.warnStock = warnStock;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(Integer sortFlag) {
        this.sortFlag = sortFlag;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties == null ? null : properties.trim();
    }
}