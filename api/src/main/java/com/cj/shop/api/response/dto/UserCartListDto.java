package com.cj.shop.api.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

/**
 * @author yuchuanWeng
 * @date 2018/8/9
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCartListDto implements Serializable {

    public UserCartListDto(long count, long page, long size) {
        this.count = count;
        this.page = page;
        this.size = size;
        calculatePages();
    }

    /**
     * 供应商名称列表
     */
    @JsonProperty("supply_array")
    private Set<String> supplyArray = new LinkedHashSet<>();

    /**
     * 购物车商品总数
     */
    private long count = 0;

    /**
     * 购物车分组后的每一项
     */
    @JsonProperty("goods_list")
    private Map<String, List<UserCartDto>> collect = new LinkedHashMap<>();

    private long page;

    private long size;

    private long totalPages;


    private void calculatePages() {
        if (count <= 0 || size <= 0) {
            totalPages = 0;
            return;
        }
        Long m = count % (long) size;
        totalPages = Math.toIntExact(count / (long) size);
        if (m > 0) {
            totalPages++;
        }
    }
}
