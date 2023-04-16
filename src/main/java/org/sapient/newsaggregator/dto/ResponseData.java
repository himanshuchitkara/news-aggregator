package org.sapient.newsaggregator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseData {
    private String status;
    private String userTier;
    private Integer total;
    private Integer startIndex;
    private Integer pageSize;
    private Integer currentPage;
    private Integer pages;
    private String orderBy;
    private List<Result> results;
}
