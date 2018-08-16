package com.cj.push.api.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索分页封装类
 *
 * @param <T>
 */
public class PagedList<T> implements Serializable{
    private int page;
    private int size;
    private long total;
    private int totalPages;
    private List<T> list;

    public PagedList(List<T> data, long total, int page, int size) {
        this.list = data;
        this.total = total;
        this.page = page;
        this.size = size;
        this.calculatePages();
    }

    private void calculatePages() {
        if(total <= 0 || size <= 0) {
            totalPages = 0;
            return;
        }
        Long m = total % (long)size;
        totalPages = Math.toIntExact(total / (long)size);
        if(m > 0){
            totalPages++;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
