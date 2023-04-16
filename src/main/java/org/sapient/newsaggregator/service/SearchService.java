package org.sapient.newsaggregator.service;

import org.sapient.newsaggregator.client.GuardianClient;
import org.sapient.newsaggregator.client.NYTimesClient;
import org.sapient.newsaggregator.dto.Doc;
import org.sapient.newsaggregator.dto.NewsAggregatorResponse;
import org.sapient.newsaggregator.dto.Result;
import org.sapient.newsaggregator.dto.SearchResponse;
import org.sapient.newsaggregator.exception.NewsAggregatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SearchService {

    @Autowired
    private GuardianClient guardianClient;
    @Autowired
    private NYTimesClient nyTimesClient;
    @Resource(name = "newsAggregatorHashMap")
    private Map<String, ArrayList<SearchResponse>> newsAggregatorHashMap;

    public NewsAggregatorResponse search(String keyword, int page, int limit) {

        if (CollectionUtils.isEmpty(newsAggregatorHashMap.get(keyword))) {
            newsAggregatorHashMap.clear();
        }

        Iterator<List<Result>> guardianResultsIterator = guardianClient.searchNewsBy(keyword);
        Iterator<List<Doc>> nyTimesResultsIterator = nyTimesClient.searchNewsBy(keyword);

        while (newsAggregatorHashMap.getOrDefault(keyword, new ArrayList<>()).size() < page * limit) {
            Set<SearchResponse> searchResponses = new LinkedHashSet<>();

            try {
                if (guardianResultsIterator.hasNext()) {
                    List<Result> results = guardianResultsIterator.next();
                    results.forEach(result -> {
                        SearchResponse searchResponse = SearchResponse.builder().id(result.getId()).type(result.getType()).sectionName(result.getSectionName()).webTitle(result.getWebTitle()).webUrl(result.getWebUrl()).apiUrl(result.getApiUrl()).materialType(result.getPillarName()).build();
                        searchResponses.add(searchResponse);
                    });
                }
            } catch (Exception e) {
                throw new NewsAggregatorException("Error while calling guardian api", e);
            }

            try {
                if (nyTimesResultsIterator.hasNext()) {
                    List<Doc> docs = nyTimesResultsIterator.next();
                    docs.forEach(doc -> {
                        SearchResponse searchResponse = SearchResponse.builder().id(doc.get_id()).type(doc.getDocument_type()).sectionName(doc.getSection_name()).webTitle(doc.getWebTitle()).webUrl(doc.getWeb_url()).apiUrl(doc.getUri()).materialType(doc.getType_of_material()).build();
                        searchResponses.add(searchResponse);
                    });
                }
            } catch (Exception e) {
                throw new NewsAggregatorException("Error while calling ny times api", e);
            }
            newsAggregatorHashMap.computeIfAbsent(keyword, k -> new ArrayList<>()).addAll(searchResponses);
        }
        return NewsAggregatorResponse.builder().status(HttpStatus.OK.toString()).data(newsAggregatorHashMap.get(keyword).subList((page - 1) * limit, page * limit)).timestamp(new Date()).build();
    }
}
