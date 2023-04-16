package org.sapient.newsaggregator.client;

import org.sapient.newsaggregator.dto.Doc;
import org.sapient.newsaggregator.dto.NYTimesSearchAPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class NYTimesClient {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${nytimes.api-key}")
    private String encryptedNYTimesAPIKey;
    private static final String NY_TIMES_BASE_URL = "https://api.nytimes.com/";
    private static final String SEARCH = "svc/search/v2/articlesearch.json";

    public Iterator<List<Doc>> searchNewsBy(String keyword) {
        return new ResponseIterator(keyword);
    }

    private class ResponseIterator implements Iterator<List<Doc>> {
        private String keyword;
        private Integer nextPage;

        public ResponseIterator(String keyword) {
            this.keyword = keyword;
            this.nextPage = 0;
        }

        @Override
        public boolean hasNext() {
            return nextPage != null;
        }

        @Override
        public List<Doc> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Reached the last page of the API response.");
            }
            NYTimesSearchAPIResponse nyTimesSearchAPIResponse = search(keyword, nextPage);
            nextPage++;
            List<Doc> newsInThisPage = nyTimesSearchAPIResponse.getResponse().getDocs();
            return newsInThisPage;
        }
    }

    private NYTimesSearchAPIResponse search(String keyword, Integer page) {
        String nyTimesAPIKey = new String(Base64.getDecoder().decode(encryptedNYTimesAPIKey));
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(String.format("%s/%s", NY_TIMES_BASE_URL, SEARCH)).queryParam("api-key", nyTimesAPIKey).queryParam("q", keyword).queryParam("page", page);

        return restTemplate.getForObject(builder.toUriString(), NYTimesSearchAPIResponse.class);
    }
}
