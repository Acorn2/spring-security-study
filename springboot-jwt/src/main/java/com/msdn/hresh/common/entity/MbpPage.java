package com.msdn.hresh.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * Mybatis Plus分页封装接口，实现Ipage接口，用于自定义sql使用
 */
@Data
public class MbpPage<T> implements IPage<T> {
    private List<T> records;
    private long total;
    private long pageSize;
    private long pageNum;
    private List<OrderItem> orders;
    private boolean optimizeCountSql;
    private boolean isSearchCount;
    private boolean hitCount;

    public MbpPage() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.pageSize = 10L;
        this.pageNum = 1L;
        this.orders = new ArrayList();
        this.optimizeCountSql = true;
        this.isSearchCount = true;
        this.hitCount = false;
    }

    public MbpPage(long pageNum, long pageSize) {
        this(pageNum, pageSize, 0L);
    }

    public MbpPage(long pageNum, long pageSize, long total) {
        this(pageNum, pageSize, total, true);
    }

    public MbpPage(long pageNum, long pageSize, boolean isSearchCount) {
        this(pageNum, pageSize, 0L, isSearchCount);
    }

    public MbpPage(long pageNum, long pageSize, long total, boolean isSearchCount) {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.pageSize = 10L;
        this.pageNum = 1L;
        this.orders = new ArrayList();
        this.optimizeCountSql = true;
        this.isSearchCount = true;
        this.hitCount = false;
        if (pageNum > 1L) {
            this.pageNum = pageNum;
        }

        this.pageSize = pageSize;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public boolean hasPrevious() {
        return this.pageNum > 1L;
    }

    public boolean hasNext() {
        return this.pageNum < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public MbpPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public MbpPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.pageSize;
    }

    @Override
    public MbpPage<T> setSize(long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.pageNum;
    }

    @Override
    public MbpPage<T> setCurrent(long current) {
        this.pageNum = current;
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return this.getOrders();
    }

    public List<OrderItem> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }
}
