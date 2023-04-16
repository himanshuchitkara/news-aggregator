package org.sapient.newsaggregator.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sapient.newsaggregator.dto.NewsAggregatorResponse;
import org.sapient.newsaggregator.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Search Controller", tags = {"Search Controller"})
@RestController
@RequestMapping(value = "/api/v1")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "Retrieve relevant news for a particular keyword input from the end user (ex: “apple”) with support for pagination", response = Object.class)
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsAggregatorResponse> search(@RequestParam() String keyword, @RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return new ResponseEntity<>(searchService.search(keyword, page, limit), HttpStatus.OK);
    }
}
