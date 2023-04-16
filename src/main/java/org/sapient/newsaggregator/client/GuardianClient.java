package org.sapient.newsaggregator.client;

import org.sapient.newsaggregator.dto.GuardianSearchAPIResponse;
import org.sapient.newsaggregator.dto.Result;
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
public class GuardianClient {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${guardian.api-key}")
    private String encryptedGuardianAPIKey;

    private static final String GUARDIAN_BASE_URL = "https://content.guardianapis.com";
    private static final String SEARCH = "search";

    public Iterator<List<Result>> searchNewsBy(String keyword) {
        return new ResponseIterator(keyword);
    }

    private class ResponseIterator implements Iterator<List<Result>> {
        private String keyword;
        private Integer nextPage;

        public ResponseIterator(String keyword) {
            this.nextPage = 1;
            this.keyword = keyword;
        }

        @Override
        public boolean hasNext() {
            return nextPage != null;
        }

        @Override
        public List<Result> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Reached the last page of the API response.");
            }
            GuardianSearchAPIResponse guardianSearchAPIResponse = search(keyword, nextPage);
            if(guardianSearchAPIResponse.getResponse().getPages() > nextPage) {
                nextPage++;
            }
            List<Result> newsInThisPage = guardianSearchAPIResponse.getResponse().getResults();
            return newsInThisPage;
        }
    }


    private GuardianSearchAPIResponse search(String keyword, Integer page) {

        String guardianAPIKey = new String(Base64.getDecoder().decode(encryptedGuardianAPIKey));
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(String.format("%s/%s", GUARDIAN_BASE_URL, SEARCH))
                .queryParam("api-key", guardianAPIKey)
                .queryParam("q", keyword)
                .queryParam("page", page);

        return restTemplate.getForObject(builder.toUriString(), GuardianSearchAPIResponse.class);
    }
}
