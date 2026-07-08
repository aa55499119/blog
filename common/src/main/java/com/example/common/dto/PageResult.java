package com.example.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private long total;
    private long page;
    private long pageSize;
    private List<T> records;

    public PageResult() {
    }

    public PageResult(long total, long page, long pageSize, List<T> records) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.records = records;
    }
}
