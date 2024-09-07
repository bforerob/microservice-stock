package com.microservice.stock.domain.model;

import java.util.List;

public class CustomPage<T> {

    private final List<T> content;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final long totalElements;
    private final Integer totalPages;

    public CustomPage(List<T> content, Integer pageNumber, Integer pageSize, long totalElements, Integer totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
